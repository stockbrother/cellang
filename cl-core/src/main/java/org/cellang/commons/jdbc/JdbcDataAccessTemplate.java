package org.cellang.commons.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JdbcDataAccessTemplate {
	private static final Logger LOG = LoggerFactory.getLogger(JdbcDataAccessTemplate.class);

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

	private static PreparedStatementExecutor<Long> UPDATE = new PreparedStatementExecutor<Long>() {

		@Override
		public Long execute(PreparedStatement ps) throws SQLException {
			int rt = ps.executeUpdate();
			return Long.valueOf(rt);//
		}

	};

	public JdbcDataAccessTemplate() {
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

	public <T> T execute(Connection con, String sql, ParameterProvider pp, PreparedStatementExecutor<T> pse) {

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

	public <T> T executeQuery(Connection con, String sql, ResultSetProcessor<T> rsp) {
		return execute(con, sql, EMPTY, new ResultSetProcessorPreparedStatementExecutor<T>(rsp));
	}

	public <T> T executeQuery(Connection con, String sql, List<Object> objects, ResultSetProcessor<T> rsp) {
		return this.executeQuery(con, sql, objects.toArray(), rsp);
	}

	public <T> T executeQuery(Connection con, String sql, Object[] objects, ResultSetProcessor<T> rsp) {
		return execute(con, sql, new ArrayParameterProvider(objects),
				new ResultSetProcessorPreparedStatementExecutor<T>(rsp));
	}

}
