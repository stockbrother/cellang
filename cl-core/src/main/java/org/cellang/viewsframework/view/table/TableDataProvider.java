package org.cellang.viewsframework.view.table;

/**
 * This interface is the data source for a table to render data cells.
 * 
 * @author wu
 *
 * @param <T>
 *            The row object type.
 */
public interface TableDataProvider<T> {

	public int getRowCount();

	public int getColumnCount();

	public ColumnDefine<T> getColumn(int col);

	public T getRowObject(int idx);

}
