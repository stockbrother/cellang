package com.sb.anyattribute.common.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.h2.jdbcx.JdbcConnectionPool;

public class ConnectionPoolWrapper {

	private static ParameterProvider EMPTY = new ParameterProvider() {

		@Override
		public int size() {
			return 0;
		}

		@Override
		public Object get(int idx) {
			throw new RuntimeException("no data");
		}
	};

	private static PreparedStatementExecutor UPDATE = new PreparedStatementExecutor() {

		@Override
		public Object execute(PreparedStatement ps) throws SQLException {
			return ps.executeUpdate();
		}

	};

	private JdbcConnectionPool pool;

	public ConnectionPoolWrapper(JdbcConnectionPool pool) {
		this.pool = pool;
	}

	public Object executeUpdate(String sql) {
		return execute(sql, EMPTY, UPDATE);
	}

	public Object executeUpdate(String sql, Object[] pp) {
		return execute(sql, new ArrayParameterProvider(pp), UPDATE);
	}

	public Object execute(String sql, ParameterProvider pp, PreparedStatementExecutor pse) {
		try {
			Connection c = pool.getConnection();
			try {

				PreparedStatement ps = c.prepareStatement(sql);
				int size = pp.size();
				for (int i = 0; i < size; i++) {
					Object obj = pp.get(i);
					ps.setObject(i + 1, obj);
				}
				try {
					return pse.execute(ps);//
				} finally {
					ps.close();
				}
			} finally {
				c.close();
			}

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	public Object executeQuery(String sql, Object[] objects, ResultSetProcessor rsp) {
		return execute(sql, new ArrayParameterProvider(objects), new ResultSetProcessorPreparedStatementExecutor(rsp));
	}

}
