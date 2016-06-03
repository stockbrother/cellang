/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 27, 2012
 */
package org.cellang.elastictable.elasticsearch.operations;

import java.util.Map;

import org.cellang.commons.util.ClassUtil;
import org.cellang.elastictable.TableRow;
import org.cellang.elastictable.TableService;
import org.cellang.elastictable.RowObject;
import org.cellang.elastictable.elasticsearch.ElasticClient;
import org.cellang.elastictable.elasticsearch.GetResponseNodeImpl;
import org.cellang.elastictable.operations.NodeGetOperationI;
import org.cellang.elastictable.result.NodeResultI;
import org.cellang.elastictable.support.OperationSupport;
import org.cellang.elastictable.support.ResultSupport;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.Client;

/**
 * @author wu
 * 
 */
public class NodeGetOperationE extends OperationSupport<NodeGetOperationI, NodeResultI> implements
		NodeGetOperationI {

	private static final String PK_UNIQUE = "uniqueId";
	private static final String PK_NODETYPE = "nodeType";

	private ElasticClient elastic;

	private static class GetResult extends ResultSupport<NodeResultI, TableRow> implements NodeResultI {

		public GetResult(TableService ds) {
			super(ds);
		}

		/*
		 * Oct 28, 2012
		 */
		@Override
		public TableRow getNode(boolean force) {
			//
			return (TableRow) super.get(force);
		}

		/*
		 * Oct 28, 2012
		 */
		@Override
		public <T extends RowObject> T wrapNode(Class<T> cls, boolean force) {
			//
			//

			//
			TableRow node = this.getNode(force);
			if (node == null) {
				if (force) {
					throw new RuntimeException("force get node by id");
				}
				return null;
			} else {
				T rt = ClassUtil.newInstance(cls);
				rt.attachTo(node, this.dataService);
				return rt;
			}
		}

	}

	/**
	 * @param ds
	 */
	public NodeGetOperationE(TableService ds) {
		super(new GetResult(ds));
		this.elastic = (ElasticClient) ds;
	}

	@Override
	public NodeResultI getResult() {
		return (org.cellang.elastictable.result.NodeResultI) this.result;
	}

	/*
	 * Oct 27, 2012
	 */
	@Override
	public NodeGetOperationI nodeType(String ntype) {
		this.parameter(PK_NODETYPE, ntype);
		return this;
	}

	/*
	 * Oct 27, 2012
	 */
	@Override
	public NodeGetOperationI uniqueId(String id) {
		this.parameter(PK_UNIQUE, id);
		return this;
	}

	/*
	 * Oct 27, 2012
	 */
	@Override
	protected void executeInternal(NodeResultI rst) throws Exception {
		Client client = elastic.getClient();
		GetResponse gr = client
				.prepareGet(this.elastic.getIndex(), this.getNodeType(true).toString(),
						this.getUniqueId(true)).execute().actionGet();
		Map<String, Object> src = gr.getSource();
		if (src == null) {
			rst.set(null);//
		} else {
			String ntype = this.getNodeType(true);

			TableRow node = new GetResponseNodeImpl(ntype, this.dataService, gr);
			rst.set(node);//
		}
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

	/*
	 * Oct 27, 2012
	 */
	@Override
	public NodeGetOperationI execute(String type, String uid) {
		return this.nodeType(type).uniqueId(uid).execute().cast();
	}

}
