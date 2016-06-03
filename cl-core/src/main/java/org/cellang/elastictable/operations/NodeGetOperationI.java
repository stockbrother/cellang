/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 27, 2012
 */
package org.cellang.elastictable.operations;

import org.cellang.elastictable.TableOperation;
import org.cellang.elastictable.result.NodeResultI;

/**
 * @author wu
 * 
 */
public interface NodeGetOperationI extends
		TableOperation<NodeGetOperationI, NodeResultI> {

	public NodeGetOperationI nodeType(String ntype);

	public NodeGetOperationI uniqueId(String uid);

	public NodeGetOperationI execute(String type, String uid);

}
