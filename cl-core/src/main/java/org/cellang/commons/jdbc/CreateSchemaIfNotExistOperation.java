package org.cellang.commons.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CreateSchemaIfNotExistOperation extends JdbcOperation<Void> {

	private String schema = "cpeatt";

	
	public CreateSchemaIfNotExistOperation(ConnectionPoolWrapper cpw) {
		super(cpw);
	}

	@Override
	public Void execute() {
		final List<String> schemaList = new ArrayList<String>();
		this.poolWrapper.executeQuery("show schemas", new ResultSetProcessor() {

			@Override
			public Object process(ResultSet rs) throws SQLException {
				while (rs.next()) {
					String name = rs.getString(1);
					schemaList.add(name);
					System.out.println(rs.getString(1));
				}
				return null;
			}
		});

		if (schemaList.contains(schema.toUpperCase())) {
			return null;//
		}

		this.poolWrapper.executeUpdate("create schema "+schema);

		return null;
	}

}
