package com.sbrother.sbook.common.jdbc;

public interface SbParameterProvider {
	public int size();

	public Object get(int idx);
}