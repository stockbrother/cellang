package com.sbrother.sbook.common.jdbc;

public abstract class SbJdbcOperation<T> {
	protected SbConnectionPoolWrapper poolWrapper;

	public SbJdbcOperation(SbConnectionPoolWrapper cpw) {
		this.poolWrapper = cpw;
	}

	public abstract T execute();
}
