package org.cellang.core.commons.jdbc;

public abstract class SqlOperation<T> extends JdbcOperation<T> {

	protected String sql;

	protected int parameterSize;

	public SqlOperation(ConnectionPoolWrapper cpw, String sql, int size) {
		super(cpw);
		this.sql = sql;
		this.parameterSize = size;
	}

	public int getParameterSize() {
		return this.parameterSize;
	}

	public String getSql() {
		return sql;
	}

}
