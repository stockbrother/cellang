package org.cellang.console.view.table;

import org.cellang.console.HasDelagates;

/**
 * This interface is the data source for a table to render data cells.
 * 
 * @author wu
 *
 * @param <T>
 *            The row object type.
 */
public interface TableDataProvider<T> extends HasDelagates {

	public int getRowCount();

	public int getColumnCount();

	public ColumnDefine getColumn(int col);

	public T getRowObject(int idx);

}
