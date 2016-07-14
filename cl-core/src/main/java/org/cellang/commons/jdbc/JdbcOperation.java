package org.cellang.commons.jdbc;

import java.sql.Connection;

public abstract class JdbcOperation<T> {
	protected JdbcDataAccessTemplate template;

	public JdbcOperation(JdbcDataAccessTemplate cpw) {
		this.template = cpw;
	}

	public abstract T execute(Connection con);
}
