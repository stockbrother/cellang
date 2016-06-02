/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 28, 2012
 */
package org.cellang.elasticnode.operations;

import org.cellang.elasticnode.ExecuteResult;
import org.cellang.elasticnode.NodeOperation;
import org.cellang.elasticnode.NodeType;
import org.cellang.elasticnode.NodeWrapper;

/**
 * @author wu
 * 
 */
public interface NodeOperationI<O extends NodeOperationI<O, W, R>, W extends NodeWrapper, R extends ExecuteResult<R, ?>>
		extends NodeOperation<O, R> {

	public NodeType getNodeType(boolean force);

	public O nodeType(NodeType ntype);

	public O nodeType(Class<W> cls);

}
