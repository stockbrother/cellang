/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 28, 2012
 */
package org.cellang.elasticnode.operations;

import org.cellang.elasticnode.NodeOperation;
import org.cellang.elasticnode.NodeWrapper;
import org.cellang.elasticnode.result.VoidResultI;

/**
 * @author wu
 * 
 */
public interface NodeDeleteOperationI<W extends NodeWrapper> extends
		NodeOperation<NodeDeleteOperationI<W>, VoidResultI> {
	public NodeDeleteOperationI<W> nodeType(Class<W> cls);

	public NodeDeleteOperationI<W> uniqueId(String id);

}
