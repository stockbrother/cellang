package org.cellang.commons.jdbc;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface PreparedStatementExecutor {
	
	public Object execute(PreparedStatement ps) throws SQLException;
	
}