/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 27, 2012
 */
package org.cellang.elasticnode.elasticsearch;

import java.util.ArrayList;
import java.util.List;

import org.cellang.commons.lang.CallbackI;
import org.cellang.commons.util.ClassUtil;
import org.cellang.elasticnode.Node;
import org.cellang.elasticnode.NodeOperation;
import org.cellang.elasticnode.NodeService;
import org.cellang.elasticnode.NodeWrapper;
import org.cellang.elasticnode.elasticsearch.operations.DumpOperationE;
import org.cellang.elasticnode.elasticsearch.operations.NodeCountOperationE;
import org.cellang.elasticnode.elasticsearch.operations.NodeCreateOperationE;
import org.cellang.elasticnode.elasticsearch.operations.NodeDeleteOperationE;
import org.cellang.elasticnode.elasticsearch.operations.NodeGetOperationE;
import org.cellang.elasticnode.elasticsearch.operations.NodeSearchOperationE;
import org.cellang.elasticnode.elasticsearch.operations.NodeUpdateOperationE;
import org.cellang.elasticnode.elasticsearch.operations.RefreshOperationE;
import org.cellang.elasticnode.meta.DataSchema;
import org.cellang.elasticnode.operations.DumpOperationI;
import org.cellang.elasticnode.operations.NodeCountOperationI;
import org.cellang.elasticnode.operations.NodeCreateOperationI;
import org.cellang.elasticnode.operations.NodeDeleteOperationI;
import org.cellang.elasticnode.operations.NodeGetOperationI;
import org.cellang.elasticnode.operations.NodeSearchOperationI;
import org.cellang.elasticnode.operations.NodeUpdateOperationI;
import org.cellang.elasticnode.operations.RefreshOperationI;
import org.cellang.elasticnode.result.NodeSearchResultI;
import org.cellang.elasticnode.support.NodeServiceSupport;
import org.elasticsearch.client.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author wu
 * 
 */
public class ElasticClientNodeServiceImpl extends NodeServiceSupport implements ElasticClient {
	private static final Logger LOG = LoggerFactory.getLogger(ElasticClientNodeServiceImpl.class);

	protected String dataVersion = "0.1";

	protected Client client;

	protected String index;

	// iso8601
	public ElasticClientNodeServiceImpl(DataSchema ds, Client client, String index) {
		super(ds);
		this.client = client;
		this.index = index;

		this.registerOperation("core.nodeget", NodeGetOperationI.class, NodeGetOperationE.class);
		this.registerOperation("core.nodecreate", NodeCreateOperationI.class, NodeCreateOperationE.class);
		this.registerOperation("core.nodesearch", NodeSearchOperationI.class, NodeSearchOperationE.class);
		this.registerOperation("core.nodecount", NodeCountOperationI.class, NodeCountOperationE.class);
		this.registerOperation("core.nodeupdate", NodeUpdateOperationI.class, NodeUpdateOperationE.class);

		this.registerOperation("core.dump", DumpOperationI.class, DumpOperationE.class);
		this.registerOperation("core.refresh", RefreshOperationI.class, RefreshOperationE.class);
		this.registerOperation("core.delete", NodeDeleteOperationI.class, NodeDeleteOperationE.class);
	}

	/*
	 * Oct 27, 2012
	 */
	@Override
	public <T extends NodeOperation> T perpareOperationInternal(Class<T> itf, Class<? extends T> cls2) {

		T rt = ClassUtil.newInstance(cls2, new Class[] { NodeService.class }, new Object[] { this });

		return rt;
	}

	/*
	 * Oct 27, 2012
	 */
	@Override
	public Client getClient() {
		//
		return this.client;
	}

	/*
	 * Oct 27, 2012
	 */
	@Override
	public <T> T executeInClient(CallbackI<Client, T> cb) {

		return cb.execute(this.client);

	}

	/*
	 * Oct 27, 2012
	 */
	@Override
	public String getIndex() {
		//
		return this.index;
	}

	/*
	 * Oct 30, 2012
	 */
	@Override
	public void refresh() {
		this.prepareOperation(RefreshOperationI.class).execute().getResult().assertNoError();
	}

	/*
	 * Nov 26, 2012
	 */
	@Override
	public <T extends NodeWrapper> T getNewestById(Class<T> wpcls, String id, boolean force) {
		//
		return this.getNewest(wpcls, Node.PK_ID, id, force);

	}

	/*
	 * Dec 8, 2012
	 */
	@Override
	public <T extends NodeWrapper> List<T> getNewestListById(Class<T> wpcls, List<String> idL, boolean force,
			boolean reserveNull) {
		//
		List<T> rt = new ArrayList<T>();
		for (String id : idL) {

			T ti = this.getNewestById(wpcls, id, force);
			if (ti == null && !reserveNull) {
				continue;
			}
			rt.add(ti);
		}

		return rt;
	}

	@Override
	public <T extends NodeWrapper> List<T> getListNewestFirst(Class<T> wpcls, String field, Object value,
			int from, int maxSize) {
		return this.getListNewestFirst(wpcls, new String[] { field }, new Object[] { value }, from, maxSize);
	}

	/*
	 * Dec 8, 2012
	 */
	@Override
	public <T extends NodeWrapper> List<T> getListNewestFirst(Class<T> wpcls, String[] fields,
			Object[] values, int from, int maxSize) {
		//
		NodeSearchOperationI<T> qo = this.prepareNodeSearch(wpcls);
		for (int i = 0; i < fields.length; i++) {
			qo.propertyEq(fields[i], values[i]);
		}
		qo.first(from);
		qo.maxSize(maxSize);
		qo.sortTimestamp(true);
		NodeSearchResultI<T> rst = qo.execute().getResult();

		return rst.list();
	}

	public void close() {
		this.client.close();
	}


}
