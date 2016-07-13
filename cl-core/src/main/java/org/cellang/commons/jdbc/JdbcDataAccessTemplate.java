package org.cellang.commons.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class JdbcDataAccessTemplate {

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

	private ConnectionProvider connectionProvider;

	public JdbcDataAccessTemplate(ConnectionProvider connectionProvider) {
		this.connectionProvider = connectionProvider;
	}

	public <T> T execute(JdbcOperation<T> op) throws SQLException {

		Connection c = this.connectionProvider.openConnection();
		if (c.getAutoCommit()) {
			throw new RuntimeException("auto commit not allowed.");
		}
		try {
			T rt = null;
			try {
				rt = op.execute(c);//
			} catch (Throwable t) {

				c.rollback();
				if (t instanceof SQLException) {
					throw (SQLException) t;
				} else if (t instanceof RuntimeException) {
					throw (RuntimeException) t;
				} else if (t instanceof Error) {
					throw (Error) t;
				} else {
					throw new RuntimeException(t);
				}
			}
			c.commit();

			return rt;
		} finally {
			c.close();
		}
	}

	public long executeUpdate(Connection con, String sql) {
		Long rt = (Long) execute(con, sql, EMPTY, UPDATE);
		return rt.longValue();
	}

	public long executeUpdate(Connection con, String sql, List<Object> pp) {
		return this.executeUpdate(con, sql, pp.toArray(new Object[pp.size()]));
	}

	public long executeUpdate(Connection con, String sql, Object[] pp) {
		return (Long) execute(con, sql, new ArrayParameterProvider(pp), UPDATE);
	}

	public Object execute(Connection con, String sql, ParameterProvider pp, PreparedStatementExecutor pse) {
		JdbcOperation<Object> op = new JdbcOperation<Object>(this) {

			@Override
			public Object execute(Connection con) {
				try {

					PreparedStatement ps = con.prepareStatement(sql);
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

				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
			}
		};

		return op.execute();

	}

	public Object executeQuery(Connection con, String sql, ResultSetProcessor rsp) {
		return execute(con, sql, EMPTY, new ResultSetProcessorPreparedStatementExecutor(rsp));
	}

	public Object executeQuery(Connection con, String sql, Object[] objects, ResultSetProcessor rsp) {
		return execute(con, sql, new ArrayParameterProvider(objects),
				new ResultSetProcessorPreparedStatementExecutor(rsp));
	}

}
