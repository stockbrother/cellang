package org.cellang.viewsframework.control;

import java.util.List;
/**
 * 
 * @author wu
 *
 */
public interface ColumnOrderable {
	public List<String> getOrderableColumnList();

	public void setOrderBy(String key);
}
