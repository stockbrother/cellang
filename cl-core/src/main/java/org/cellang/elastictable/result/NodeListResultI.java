/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 30, 2012
 */
package org.cellang.elastictable.result;

import java.util.List;

import org.cellang.elastictable.ExecuteResult;
import org.cellang.elastictable.TableRow;
import org.cellang.elastictable.RowObject;

/**
 * @author wu
 * 
 */
public interface NodeListResultI<R extends ExecuteResult<R, List<T>>, T extends TableRow>
		extends ListResultI<R, T> {

	public <X extends RowObject> List<X> list(Class<X> class1);

}
