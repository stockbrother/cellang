package org.cellang.commons.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


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
			int rt = ps.executeUpdate();
			return Long.valueOf(rt);//
		}

	};

	public ConnectionPoolWrapper() {

	}

	public long executeUpdate(String sql) {
		Long rt = (Long) execute(sql, EMPTY, UPDATE);
		return rt.longValue();
	}

	public Long executeUpdate(String sql, Object[] pp) {
		return (Long) execute(sql, new ArrayParameterProvider(pp), UPDATE);
	}

	public Object execute(String sql, ParameterProvider pp, PreparedStatementExecutor pse) {
		try {
			Connection c = null;//
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

	public Object executeQuery(String sql, ResultSetProcessor rsp) {
		return execute(sql, EMPTY, new ResultSetProcessorPreparedStatementExecutor(rsp));
	}

	public Object executeQuery(String sql, Object[] objects, ResultSetProcessor rsp) {
		return execute(sql, new ArrayParameterProvider(objects), new ResultSetProcessorPreparedStatementExecutor(rsp));
	}

}
