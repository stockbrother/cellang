package org.cellang.commons.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class JdbcOperation<T> {
	protected JdbcDataAccessTemplate template;

	public JdbcOperation(JdbcDataAccessTemplate cpw) {
		this.template = cpw;
	}

	public T execute() {

		try {
			return this.template.execute(this);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public abstract T execute(Connection con);
}
