package com.sbrother.sbook.common.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.h2.jdbcx.JdbcConnectionPool;

public class SbConnectionPoolWrapper {

	private static SbParameterProvider EMPTY = new SbParameterProvider() {

		@Override
		public int size() {
			return 0;
		}

		@Override
		public Object get(int idx) {
			throw new RuntimeException("no data");
		}
	};

	private static SbPreparedStatementExecutor UPDATE = new SbPreparedStatementExecutor() {

		@Override
		public Object execute(PreparedStatement ps) throws SQLException {
			return ps.executeUpdate();
		}

	};

	private JdbcConnectionPool pool;

	public SbConnectionPoolWrapper(JdbcConnectionPool pool) {
		this.pool = pool;
	}

	public Object executeUpdate(String sql) {
		return execute(sql, EMPTY, UPDATE);
	}

	public Object executeUpdate(String sql, Object[] pp) {
		return execute(sql, new SbArrayParameterProvider(pp), UPDATE);
	}

	public Object execute(String sql, SbParameterProvider pp, SbPreparedStatementExecutor pse) {
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

	public Object executeQuery(String sql, Object[] objects, SbResultSetProcessor rsp) {
		return execute(sql, new SbArrayParameterProvider(objects), new SbResultSetProcessorPreparedStatementExecutor(rsp));
	}

}
