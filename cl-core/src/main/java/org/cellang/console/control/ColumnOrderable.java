package org.cellang.console.control;

import java.util.List;

public interface ColumnOrderable {
	public List<String> getOrderableColumnList();

	public void setOrderBy(String key);
}
