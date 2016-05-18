package com.sb.anyattribute.core.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.h2.jdbcx.JdbcConnectionPool;

public class AttributeTable {

	public static interface ParameterProvider {
		public int size();

		public Object get(int idx);
	}

	public static interface PreparedStatementExecutor {
		public Object execute(PreparedStatement ps) throws SQLException;
	}

	public static interface ResultSetProcessor {
		public Object process(ResultSet rs) throws SQLException;
	}

	public static class ArrayParameterProvider implements ParameterProvider {

		Object[] values;

		public ArrayParameterProvider(Object[] pA) {
			this.values = pA;
		}

		@Override
		public int size() {
			return this.values.length;
		}

		@Override
		public Object get(int idx) {
			return this.values[idx];
		}

	}

	public static class ResultSetProcessorPreparedStatementExecutor implements PreparedStatementExecutor {
		private ResultSetProcessor rsp;

		public ResultSetProcessorPreparedStatementExecutor(ResultSetProcessor rsp) {
			this.rsp = rsp;
		}

		@Override
		public Object execute(PreparedStatement ps) throws SQLException {
			ResultSet rs = ps.executeQuery();
			return rsp.process(rs);
		}

	}
	
	private static final String SQL_CREATE = "create table if not exists attributes(owner varchar,date timestamp,name varchar,value decimal) ";
	public static final String SQL_INSERT = "insert into attributes(owner,date,name,value)values(?,?,?,?)";
	public static final String SQL_SELECT = "select owner,date,name,value from attributes where owner=? and date=? and name=?";

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

	public static Object createIfNotExist(JdbcConnectionPool p) {
		return executeUpdate(p, SQL_CREATE);
	}

	public static Object executeUpdate(JdbcConnectionPool p, String sql) {
		return execute(p, sql, EMPTY, UPDATE);
	}

	public static Object executeUpdate(JdbcConnectionPool p, String sql, Object[] pp) {
		return execute(p, sql, new ArrayParameterProvider(pp), UPDATE);
	}

	public static Object execute(JdbcConnectionPool p, String sql, ParameterProvider pp, PreparedStatementExecutor pse) {
		try {
			Connection c = p.getConnection();
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

	public static Object executeQuery(JdbcConnectionPool p, String sql, Object[] objects, ResultSetProcessor rsp) {
		return execute(p, sql, new ArrayParameterProvider(objects), new ResultSetProcessorPreparedStatementExecutor(rsp));
	}

}
