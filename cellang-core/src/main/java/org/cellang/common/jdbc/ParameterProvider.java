package org.cellang.common.jdbc;

public interface ParameterProvider {
	public int size();

	public Object get(int idx);
}