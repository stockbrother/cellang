package org.cellang.console.control;

import java.util.List;

public interface ColumnAppendable {

	public List<String> getExtenableColumnList();

	public void appendColumn(String columnName);

}
