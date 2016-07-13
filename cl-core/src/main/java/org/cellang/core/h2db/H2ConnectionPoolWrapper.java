package org.cellang.core.h2db;

import java.sql.Connection;
import java.sql.SQLException;

import org.cellang.commons.jdbc.ConnectionProvider;
import org.h2.jdbcx.JdbcConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class H2ConnectionPoolWrapper implements ConnectionProvider {
	private static final Logger LOG = LoggerFactory.getLogger(H2ConnectionPoolWrapper.class);

	private JdbcConnectionPool pool;

	public H2ConnectionPoolWrapper(JdbcConnectionPool pool) {
		this.pool = pool;
	}

	@Override
	public Connection openConnection() throws SQLException {

		Connection c = this.pool.getConnection();
		if (c.getAutoCommit()) {
			c.setAutoCommit(false);//
			LOG.debug("autocommit disabled for connection:" + c);
		}
		LOG.debug("connection opened.");
		return c;
	}

	public static ConnectionProvider newInstance(String dbUrl, String string, String string2) {
		JdbcConnectionPool pool = JdbcConnectionPool.create(dbUrl, "sa", "sa");
		LOG.info("connection pool created");
		return new H2ConnectionPoolWrapper(pool);
	}

	public void dispose() {
		this.pool.dispose();
		LOG.info("connection pool closed");//
	}

	@Override
	public void closeConnection(Connection con) throws SQLException {
		con.close();
	}

}
