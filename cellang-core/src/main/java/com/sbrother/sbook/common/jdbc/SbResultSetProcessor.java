package com.sbrother.sbook.common.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface SbResultSetProcessor {
	public Object process(ResultSet rs) throws SQLException;
}