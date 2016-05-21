package org.cellang.common.jdbc;

public abstract class JdbcOperation<T> {
	protected ConnectionPoolWrapper poolWrapper;

	public JdbcOperation(ConnectionPoolWrapper cpw) {
		this.poolWrapper = cpw;
	}

	public abstract T execute();
}
