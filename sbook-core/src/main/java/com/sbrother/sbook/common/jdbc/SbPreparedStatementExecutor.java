package com.sbrother.sbook.common.jdbc;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface SbPreparedStatementExecutor {
	
	public Object execute(PreparedStatement ps) throws SQLException;
	
}