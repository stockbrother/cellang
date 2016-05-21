package org.cellang.core.jdbc.operation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.cellang.common.jdbc.ConnectionPoolWrapper;
import org.cellang.common.jdbc.ResultSetProcessor;
import org.cellang.common.jdbc.SqlOperation;
import org.cellang.core.jdbc.PropertiesTable;

public class GetPropertiesOperation extends SqlOperation<Properties> {

	private static String SQL0 = "select * from " + PropertiesTable.T_PROPERTIES;

	public GetPropertiesOperation(ConnectionPoolWrapper cpw) {
		super(cpw, SQL0, 0);
	}

	@Override
	public Properties execute() {
		Properties rt = new Properties();
		this.poolWrapper.executeQuery(SQL0, new ResultSetProcessor() {

			@Override
			public Object process(ResultSet rs) throws SQLException {
				while (rs.next()) {
					String key = rs.getString(PropertiesTable.F_NAME.getName());
					String value = rs.getString(PropertiesTable.F_VALUE.getName());
					rt.setProperty(key, value);
				}
				return rt;
			}
		});
		return rt;
	}

}
