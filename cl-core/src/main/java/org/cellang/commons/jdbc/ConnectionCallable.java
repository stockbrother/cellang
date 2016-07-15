package org.cellang.commons.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionCallable<T> {
	public T call(Connection con) throws SQLException;
}
