/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 28, 2012
 */
package org.cellang.elastictable.operations;

import org.cellang.elastictable.TableOperation;
import org.cellang.elastictable.RowObject;
import org.cellang.elastictable.result.VoidResultI;

/**
 * @author wu
 * 
 */
public interface NodeDeleteOperationI<W extends RowObject> extends
		TableOperation<NodeDeleteOperationI<W>, VoidResultI> {
	public NodeDeleteOperationI<W> nodeType(Class<W> cls);

	public NodeDeleteOperationI<W> uniqueId(String id);

}
