package org.cellang.commons.jdbc;

public interface ParameterProvider {
	public int size();

	public Object get(int idx);
}