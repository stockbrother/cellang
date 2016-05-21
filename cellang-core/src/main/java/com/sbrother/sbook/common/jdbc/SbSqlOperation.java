package com.sbrother.sbook.common.jdbc;

public abstract class SbSqlOperation<T> extends SbJdbcOperation<T> {

	protected String sql;

	protected int parameterSize;

	public SbSqlOperation(SbConnectionPoolWrapper cpw, String sql, int size) {
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
