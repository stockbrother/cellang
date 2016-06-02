/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 27, 2012
 */
package org.cellang.elasticnode.operations;

import org.cellang.elasticnode.NodeOperation;
import org.cellang.elasticnode.NodeType;
import org.cellang.elasticnode.result.NodeResultI;

/**
 * @author wu
 * 
 */
public interface NodeGetOperationI extends
		NodeOperation<NodeGetOperationI, NodeResultI> {

	public NodeGetOperationI nodeType(NodeType ntype);

	public NodeGetOperationI uniqueId(String uid);

	public NodeGetOperationI execute(NodeType type, String uid);

}
