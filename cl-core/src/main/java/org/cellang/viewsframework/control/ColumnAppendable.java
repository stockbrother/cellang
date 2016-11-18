package org.cellang.viewsframework.control;

import java.util.List;

/**
 * Table's column is changeable. A new column with name can be appended into
 * this table.
 * 
 * @author wu
 *
 */
public interface ColumnAppendable {
	/**
	 * All additional column names.
	 * 
	 * @return
	 */
	public List<String> getExtenableColumnList();

	/**
	 * Append a column to the table.
	 * 
	 * @param columnName
	 */
	public void appendColumn(String columnName);

}
