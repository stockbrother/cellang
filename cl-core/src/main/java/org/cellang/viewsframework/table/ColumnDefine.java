package org.cellang.viewsframework.table;

/**
 * @author wu
 *
 * @param <T>
 */
public interface ColumnDefine<T> {

	public String getColumnName(int index);

	public Class<?> getValueRenderingClass();

	public Object getValue(int rowIdx, T rowObj);

}
