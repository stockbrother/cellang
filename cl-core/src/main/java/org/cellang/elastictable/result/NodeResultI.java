/**
 *  Nov 28, 2012
 */
package org.cellang.elastictable.result;

import org.cellang.elastictable.ExecuteResult;
import org.cellang.elastictable.TableRow;
import org.cellang.elastictable.RowObject;

public interface NodeResultI extends ExecuteResult<NodeResultI, TableRow> {

	public TableRow getNode(boolean force);

	public <T extends RowObject> T wrapNode(Class<T> cls, boolean force);

}