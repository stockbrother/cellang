package org.cellang.core.common.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ResultSetProcessor {
	public Object process(ResultSet rs) throws SQLException;
}