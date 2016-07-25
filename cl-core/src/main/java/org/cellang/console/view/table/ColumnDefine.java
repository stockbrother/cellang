package org.cellang.console.view.table;

public interface ColumnDefine<T> {

	public String getColumnName(int index);

	public Class<?> getValueRenderingClass();

	public Object getValue(int rowIdx, T rowObj);
	
}
