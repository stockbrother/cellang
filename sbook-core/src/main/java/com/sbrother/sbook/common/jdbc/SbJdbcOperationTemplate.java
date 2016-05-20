package com.sbrother.sbook.common.jdbc;

public abstract class SbJdbcOperationTemplate<T> {

	public SbJdbcOperationTemplate() {
	}

	public abstract T execute(SbConnectionPoolWrapper cpw);
	
}
