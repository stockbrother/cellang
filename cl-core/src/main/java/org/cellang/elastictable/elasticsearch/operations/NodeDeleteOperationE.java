/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 27, 2012
 */
package org.cellang.elastictable.elasticsearch.operations;

import org.cellang.elastictable.TableService;
import org.cellang.elastictable.RowObject;
import org.cellang.elastictable.elasticsearch.ElasticClient;
import org.cellang.elastictable.meta.NodeMeta;
import org.cellang.elastictable.operations.NodeDeleteOperationI;
import org.cellang.elastictable.result.VoidResultI;
import org.cellang.elastictable.support.OperationSupport;
import org.cellang.elastictable.support.VoidResult;
import org.elasticsearch.action.delete.DeleteRequestBuilder;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.client.Client;

/**
 * @author wu
 * 
 */
public class NodeDeleteOperationE<W extends RowObject> extends
		OperationSupport<NodeDeleteOperationI<W>, VoidResultI> implements NodeDeleteOperationI<W> {

	private static final String PK_UNIQUE = "uniqueId";
	private static final String PK_NODETYPE = "nodeType";
	private static final String PK_WRAPPER_CLS = "wrapperCls";

	private ElasticClient elastic;

	protected NodeMeta nodeConfig;

	/**
	 * @param ds
	 */
	public NodeDeleteOperationE(TableService ds) {
		super(new VoidResult(ds));
		this.elastic = (ElasticClient) ds;
	}

	/*
	 * Dec 30, 2012
	 */
	@Override
	public NodeDeleteOperationI<W> nodeType(Class<W> cls) {
		NodeMeta nc = this.dataService.getConfigurations().getNodeConfig(cls, true);
		this.nodeType(nc);
		return this;
	}

	/**
	 * Nov 28, 2012
	 */
	private void nodeType(NodeMeta nc) {
		this.parameter(PK_NODETYPE, nc.getNodeType());
		this.parameter(PK_WRAPPER_CLS, nc.getWrapperClass());
		this.nodeConfig = nc;
	}

	/*
	 * Oct 27, 2012
	 */
	@Override
	public NodeDeleteOperationI<W> uniqueId(String id) {
		this.parameter(PK_UNIQUE, id);
		return this;
	}

	/*
	 * Oct 27, 2012
	 */
	@Override
	protected void executeInternal(VoidResultI rst) throws Exception {
		Client client = elastic.getClient();

		DeleteRequestBuilder drb = client.prepareDelete();
		drb.setIndex(elastic.getIndex());
		drb.setType(this.getNodeType(true).toString());
		drb.setId(this.getUniqueId(true));//
		drb.setRefresh(true);

		DeleteResponse gr = drb.execute().actionGet();

		rst.set(gr.isFound());//

	}

	/**
	 * Oct 27, 2012
	 */
	private String getUniqueId(boolean force) {
		//
		return (String) this.getParameter(PK_UNIQUE, force);

	}

	/**
	 * Oct 27, 2012
	 */
	private String getNodeType(boolean force) {
		//
		return (String) this.getParameter(PK_NODETYPE, force);
	}

}
