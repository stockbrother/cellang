package org.cellang.console.model;

import org.cellang.console.view.HasDelagates;

public interface TableDataProvider<T> extends HasDelagates {

	public int getRowCount();

	public int getColumnCount();

	public String getColumnName(int column);

	public Object getValueAt(int rowIndex, int columnIndex);

	public T getRowObject(int idx);

}
