<<<<<<< HEAD:cl-core/src/main/java/org/cellang/console/view/table/TableDataProvider.java
package org.cellang.console.view.table;

import org.cellang.console.HasDelagates;
=======
package org.cellang.console.model;

import org.cellang.console.view.HasDelagates;
>>>>>>> 2b639be440c5b20f5f0b6ae76cdc9ca83773fbaf:cl-core/src/main/java/org/cellang/console/model/TableDataProvider.java

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
