package com.sb.anyattribute.common.jdbc;

public interface ParameterProvider {
	public int size();

	public Object get(int idx);
}