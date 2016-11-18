package org.cellang.viewsframework.control;

public interface Filterable {
	
	public String[] getFilterableColumnList();

	public void setLike(String column, String value);

	public void unsetLike(String key);
		
}
