/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 27, 2012
 */
package org.cellang.elasticnode.elasticsearch;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import org.cellang.commons.Configuration;
import org.cellang.core.util.ExceptionUtil;
import org.cellang.elasticnode.NodeServiceFactory;
import org.cellang.elasticnode.NodeService;
import org.cellang.elasticnode.NodeType;
import org.cellang.elasticnode.meta.AnalyzerType;
import org.cellang.elasticnode.meta.DataSchema;
import org.cellang.elasticnode.meta.FieldMeta;
import org.cellang.elasticnode.meta.FieldType;
import org.cellang.elasticnode.meta.NodeMeta;
import org.elasticsearch.action.ListenableActionFuture;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequestBuilder;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequestBuilder;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.json.JsonXContent;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author wu
 * 
 */
public class ElasticDataServiceFactoryImpl implements NodeServiceFactory {

	private static final Logger LOG = LoggerFactory.getLogger(ElasticDataServiceFactoryImpl.class);

	protected String defaultIndex = "nodes";

	protected DataSchema schema;

	protected ElasticClientNodeServiceImpl dataService;

	protected final ReentrantLock dsLock = new ReentrantLock();
	
	protected Configuration config;

	public ElasticDataServiceFactoryImpl() {
		this.schema = new DataSchema();
	}

	/*
	 * Jan 20, 2013
	 */
	@Override
	public DataSchema getSchema() {
		return this.schema;
	}

	@Override
	public void close() {
		if (this.dataService == null) {
			return;
		}
		this.dataService.close();
		this.dataService = null;
	}

	private String getHost() {
		return this.config.getString("host", "locahost");
	}

	private int getPort() {
		return this.config.getAsInt("port", 9300);//
	}

	/*
	 * Jan 20, 2013
	 */
	@Override
	public NodeService getDataService() {
		return this.getDataService(this.defaultIndex);
	}

	private NodeService getDataService(String index) {
		//
		if (!this.defaultIndex.equals(index)) {
			throw new RuntimeException("todo");
		}
		if (this.dataService != null) {
			return this.dataService;
		}
		this.dsLock.lock();
		try {
			if (this.dataService != null) {
				return this.dataService;
			}

			return this.doCreateDataService(index);

		} finally {
			this.dsLock.unlock();
		}
	}

	private NodeService doCreateDataService(String index) {

		this.schema.freeze();// not allow change from now on;
		TransportClient client = TransportClient.builder().build();//TODO
		String hostS = this.getHost();
		InetAddress host;
		try {
			host = InetAddress.getByName(hostS);
		} catch (UnknownHostException e) {
			throw ExceptionUtil.toRuntimeException(e);//
		}
		
		int port = this.getPort();
		client.addTransportAddress(new InetSocketTransportAddress(host, port));

		MetaInfo mi = this.loadMetaInfos(client, index, false);
		LOG.info("meta info readed from data base:" + mi);
		if (mi == null) {// no index init

			this.createIndex(client, index);
		} else {//index and the meta-info exist

			// check is meet
			MetaInfo expected = this.getExpectedMetaInfos();

			if (expected.equals(mi)) {

				LOG.info("data version is ok:" + mi.getVersion());
			} else {
				throw new RuntimeException("data-meta info is not match,expected:" + expected + ",actrual:" + mi
						+ ",please make sure your elastic server configureation is correct.");
			}

			boolean ci = this.config.getBoolean("cleanAtInit", false);
			boolean test = this.config.getBoolean("isTest", false);
			if (ci) {
				if (!test) {
					throw new RuntimeException("not in test mode,clean data in product mode not allowed!");
				}
				this.deleteIndex(client, index);
				this.createIndex(client, index);//
			}

		}

		this.dataService = new ElasticClientNodeServiceImpl(this.schema, client, index);

		return this.dataService;
	}

	/**
	 * <code>
	 wu@thinkpad:~/git/fsi/fs-uiclient$ curl -XGET 'http://localhost:9200/node-index/expectation/_mapping?pretty=true'
{
  "expectation" : {
    "properties" : {
      "accountId" : {
        "type" : "string"
      },
      "body" : {
        "type" : "string",
        "analyzer" : "text",
        "store" : "yes"
      },
      "id_" : {
        "type" : "string"
      },
      "timestamp_" : {
        "type" : "date",
        "format" : "dateOptionalTime"
      },
      "type_" : {
        "type" : "string"
      },
      "uniqueId_" : {
        "type" : "string"
      }
    }
  }
}
	 * </code> Jan 20, 2013
	 */
	private void createIndex(Client client, String index) {
		LOG.info("createIndex:" + index + ",...");
		IndicesAdminClient iac = client.admin().indices();
		{
			CreateIndexRequestBuilder cib = iac.prepareCreate(index);

			{
				XContentBuilder jb;
				try {
					jb = JsonXContent.contentBuilder();
					jb.startObject();
					{

						jb.field("index.number_of_shards", 2);
						// jb.field("number_of_replicas", 1);//
						jb.field("index.analysis.analyzer.default.type", "keyword");//
						jb.field("analysis.analyzer." + AnalyzerType.TEXT + ".type", "standard");
						jb.field("analysis.analyzer." + AnalyzerType.TEXT + ".max_token_length", 255);

					}
					jb.endObject();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
				cib.setSettings(jb);
			}
			ListenableActionFuture<CreateIndexResponse> af = cib.execute();
			// Throwable t = af.getRootFailure();
			// if (t != null) {
			// throw RuntimeException.toRtE(t);
			// }
			CreateIndexResponse res = af.actionGet();

			boolean ack = res.isAcknowledged();

			if (!ack) {
				throw new RuntimeException("failed to create index:" + index);
			}
		}

		List<NodeMeta> nmL = this.schema.getNodeMetaList();
		for (NodeMeta nm : nmL) {

			PutMappingRequestBuilder mrb = iac.preparePutMapping(index);
			NodeType ntype = nm.getNodeType();
			String type = ntype.toString();
			mrb.setType(type);
			XContentBuilder jb;
			try {
				jb = JsonXContent.contentBuilder();
				jb.startObject();

				jb.startObject(type);

				jb.startObject("properties");
				for (FieldMeta fm : nm.getFieldList()) {
					String fname = fm.getName();
					jb.startObject(fname);

					FieldType ftype = fm.getType();
					jb.field("type", ftype.toString());

					if (ftype.equals(FieldType.DATE)) {
						jb.field("format", "dateOptionalTime");
					}

					jb.field("store", "yes");// TODO?

					AnalyzerType analyzer = fm.getAnalyzer();
					if (analyzer != null) {
						jb.field("analyzer", analyzer.toString());
					}

					jb.endObject();//
				}
				jb.endObject();//

				jb.endObject();//

				jb.endObject();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}

			mrb.setSource(jb);
			ListenableActionFuture<PutMappingResponse> af = mrb.execute();

			PutMappingResponse res = af.actionGet();
			if (!res.isAcknowledged()) {
				throw new RuntimeException("failed mapping for type:" + type + " with config:" + nm);
			}
			LOG.info("mapping type:" + type + ",meta:" + nm);

		}// end mapping
			// create version meta info
		{
			MetaInfo mi = this.getExpectedMetaInfos();// for create index.
			this.saveMetaInfo(mi, client, index);
		}
	}

	protected void saveMetaInfo(MetaInfo mi, Client client, String index) {
		for (String key : mi.keyList()) {

			String value = mi.getProperty(key, true);

			try {
				XContentBuilder jb = JsonXContent.contentBuilder();
				jb.startObject();
				jb.field("key",key);
				jb.field("value", value);//
				jb.endObject();

				IndexResponse response = client.prepareIndex(index, MetaInfo.TYPE).setSource(jb)
						.execute().actionGet();
				String rid = response.getId();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

	}

	protected MetaInfo getExpectedMetaInfos() {
		Configuration mc = null;//TODO //Configuration.properties(this.configId + ".metaInfo");

		String v = mc.getString(MetaInfo.PK_VERSION, true);
		String o = mc.getString(MetaInfo.PK_OWNER, true);
		String p = mc.getString(MetaInfo.PK_PASSWORD, true);

		MetaInfo rt = new MetaInfo();
		rt.setProperty(MetaInfo.PK_VERSION, v);
		rt.setProperty(MetaInfo.PK_OWNER, o);
		rt.setProperty(MetaInfo.PK_PASSWORD, p);
		return rt;
	}

	private void deleteIndex(Client client, String index) {
		LOG.warn("delete index:" + index);
		IndicesAdminClient iac = client.admin().indices();
		// this.dataService.refresh();//
		DeleteIndexRequestBuilder rb = iac.prepareDelete(index);

		DeleteIndexResponse gr = rb.execute().actionGet();
		boolean ack = gr.isAcknowledged();
		if (!ack) {
			throw new RuntimeException("failed to delete index :" + index);
		}
	}

	private boolean isIndexExist(Client client, String index) {
		IndicesAdminClient iac = client.admin().indices();
		IndicesExistsResponse ier = iac.prepareExists(index).execute().actionGet();
		return ier.isExists();
	}

	private MetaInfo loadMetaInfos(Client client, String index, boolean force) {
		if (!this.isIndexExist(client, index)) {
			return null;
		}

		SearchRequestBuilder sr = client.prepareSearch(index).setTypes(MetaInfo.TYPE);
		SearchResponse sb = sr.execute().actionGet();
		MetaInfo rt = new MetaInfo();
		SearchHits shs = sb.getHits();

		for (SearchHit sh : sb.getHits()) {

			Map<String, Object> old = sh.sourceAsMap();
			String key = (String) old.get("key");
			String value = (String) old.get("value");
			rt.setProperty(key, value);
		}

		return rt;
	}

}
