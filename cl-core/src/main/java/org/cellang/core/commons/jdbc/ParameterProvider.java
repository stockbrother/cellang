package org.cellang.core.commons.jdbc;

public interface ParameterProvider {
	public int size();

	public Object get(int idx);
}