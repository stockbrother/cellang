/**
 *  Nov 28, 2012
 */
package org.cellang.elasticnode.result;

import org.cellang.elasticnode.ExecuteResult;
import org.cellang.elasticnode.Node;
import org.cellang.elasticnode.NodeWrapper;

public interface NodeResultI extends ExecuteResult<NodeResultI, Node> {

	public Node getNode(boolean force);

	public <T extends NodeWrapper> T wrapNode(Class<T> cls, boolean force);

}