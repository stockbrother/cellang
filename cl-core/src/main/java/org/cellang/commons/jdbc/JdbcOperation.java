package org.cellang.commons.jdbc;

import java.sql.Connection;

public abstract class JdbcOperation<T> {
	protected JdbcDataAccessTemplate template;

	public JdbcOperation() {
		this.template = new JdbcDataAccessTemplate();
	}

	public abstract T execute(Connection con);
}
