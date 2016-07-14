package org.cellang.commons.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionProvider {
	public Connection openConnection() throws SQLException;

	public void closeConnection(Connection con) throws SQLException;
	
	public void dispose();
}
