package org.cellang.common.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ResultSetProcessorPreparedStatementExecutor implements PreparedStatementExecutor {
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