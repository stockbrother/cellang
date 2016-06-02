/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 27, 2012
 */
package org.cellang.elasticnode.elasticsearch.operations;

import java.util.Map;

import org.cellang.commons.util.ClassUtil;
import org.cellang.elasticnode.Node;
import org.cellang.elasticnode.NodeService;
import org.cellang.elasticnode.NodeType;
import org.cellang.elasticnode.NodeWrapper;
import org.cellang.elasticnode.elasticsearch.ElasticClient;
import org.cellang.elasticnode.elasticsearch.GetResponseNodeImpl;
import org.cellang.elasticnode.operations.NodeGetOperationI;
import org.cellang.elasticnode.result.NodeResultI;
import org.cellang.elasticnode.support.OperationSupport;
import org.cellang.elasticnode.support.ResultSupport;
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

	private static class GetResult extends ResultSupport<NodeResultI, Node> implements NodeResultI {

		public GetResult(NodeService ds) {
			super(ds);
		}

		/*
		 * Oct 28, 2012
		 */
		@Override
		public Node getNode(boolean force) {
			//
			return (Node) super.get(force);
		}

		/*
		 * Oct 28, 2012
		 */
		@Override
		public <T extends NodeWrapper> T wrapNode(Class<T> cls, boolean force) {
			//
			//

			//
			Node node = this.getNode(force);
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
	public NodeGetOperationE(NodeService ds) {
		super(new GetResult(ds));
		this.elastic = (ElasticClient) ds;
	}

	@Override
	public NodeResultI getResult() {
		return (org.cellang.elasticnode.result.NodeResultI) this.result;
	}

	/*
	 * Oct 27, 2012
	 */
	@Override
	public NodeGetOperationI nodeType(NodeType ntype) {
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
			NodeType ntype = this.getNodeType(true);

			Node node = new GetResponseNodeImpl(ntype, this.dataService, gr);
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
	private NodeType getNodeType(boolean force) {
		//
		return (NodeType) this.getParameter(PK_NODETYPE, force);
	}

	/*
	 * Oct 27, 2012
	 */
	@Override
	public NodeGetOperationI execute(NodeType type, String uid) {
		return this.nodeType(type).uniqueId(uid).execute().cast();
	}

}
