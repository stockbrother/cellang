/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 28, 2012
 */
package org.cellang.elastictable.operations;

import org.cellang.elastictable.ExecuteResult;
import org.cellang.elastictable.TableOperation;
import org.cellang.elastictable.RowObject;

/**
 * @author wu
 * 
 */
public interface NodeOperationI<O extends NodeOperationI<O, W, R>, W extends RowObject, R extends ExecuteResult<R, ?>>
		extends TableOperation<O, R> {

	public String getNodeType(boolean force);

	public O nodeType(String ntype);

	public O nodeType(Class<W> cls);

}
