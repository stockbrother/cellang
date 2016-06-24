package org.cellang.core.h2db;

import java.sql.Connection;
import java.sql.SQLException;

import org.cellang.commons.jdbc.ConnectionPoolWrapper;
import org.h2.jdbcx.JdbcConnectionPool;

public class H2ConnectionPoolWrapper extends ConnectionPoolWrapper{
	private JdbcConnectionPool pool;
	public H2ConnectionPoolWrapper(JdbcConnectionPool pool) {
		this.pool = pool;
	}

	@Override
	public Connection openConnection() throws SQLException {
		return this.pool.getConnection();
	}

	public static ConnectionPoolWrapper newInstance(String dbUrl, String string, String string2) {
		JdbcConnectionPool pool = JdbcConnectionPool.create(dbUrl, "sa", "sa");
		
		return new H2ConnectionPoolWrapper(pool);
	}

	@Override
	public void close() {
		this.pool.dispose();
	}

}
