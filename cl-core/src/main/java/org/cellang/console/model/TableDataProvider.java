package org.cellang.console.model;

import org.cellang.console.view.HasDelagates;

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

	public String getColumnName(int column);

	public Object getValueAt(int rowIndex, int columnIndex);

	public T getRowObject(int idx);

	public Class<?> getColumnClass(int index);
	
}
