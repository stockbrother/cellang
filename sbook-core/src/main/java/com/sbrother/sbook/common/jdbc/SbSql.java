package com.sbrother.sbook.common.jdbc;

public class SbSql {

	private String sql;

	private int parameterSize;

	public SbSql(String sql, int size) {
		this.sql = sql;
		this.parameterSize = size;
	}

	public int getParameterSize() {
		return this.parameterSize;
	}

	public static SbSql getInstance(String string) {
		return getInstance(string, 0);
	}

	public static SbSql getInstance(String string, int size) {
		return new SbSql(string, size);
	}

	public String getSql() {
		return sql;
	}

}
