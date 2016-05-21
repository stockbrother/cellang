package com.sbrother.sbook.common.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SbResultSetProcessorPreparedStatementExecutor implements SbPreparedStatementExecutor {
	private SbResultSetProcessor rsp;

	public SbResultSetProcessorPreparedStatementExecutor(SbResultSetProcessor rsp) {
		this.rsp = rsp;
	}

	@Override
	public Object execute(PreparedStatement ps) throws SQLException {
		ResultSet rs = ps.executeQuery();
		return rsp.process(rs);
	}

}