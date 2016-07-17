package org.cellang.console.control;

public interface Filterable {
	
	public String[] getColumn();

	public void setLike(String column, String value);

	public void unsetLike(String key);
		
}
