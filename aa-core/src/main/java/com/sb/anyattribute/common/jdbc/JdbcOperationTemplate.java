package com.sb.anyattribute.common.jdbc;

public abstract class JdbcOperationTemplate<T> {

	public JdbcOperationTemplate() {
	}

	public abstract T execute(ConnectionPoolWrapper cpw);
	
}
