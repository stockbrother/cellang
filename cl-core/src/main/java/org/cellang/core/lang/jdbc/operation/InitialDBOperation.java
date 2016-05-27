package org.cellang.core.lang.jdbc.operation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.cellang.commons.jdbc.ConnectionPoolWrapper;
import org.cellang.commons.jdbc.JdbcOperation;
import org.cellang.commons.jdbc.ResultSetProcessor;
import org.cellang.core.lang.jdbc.CellsTable;
import org.cellang.core.lang.jdbc.PropertiesTable;
import org.cellang.core.lang.jdbc.RelationsTable;

public class InitialDBOperation extends JdbcOperation<Void> {

	private String dataVersion = "0.0.1";

	private static String SQL0 = "create table " + PropertiesTable.T_PROPERTIES + "(name varchar,value varchar) ";
	private static String SQL1 = "create table " + CellsTable.T_CELLS + "(id varchar,typeid varchar,value decimal) ";
	private static String SQL2 = "create table " + RelationsTable.T_RELATIONS
			+ "(id1 varchar,typeid varchar,id2 varchar) ";

	public InitialDBOperation(ConnectionPoolWrapper cpw) {
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
		
		if(schemaList.contains("CELLANG")){
			return null;//
		}
		
		this.poolWrapper.executeUpdate("create schema cellang");
		
		this.poolWrapper.executeUpdate(SQL0);

		this.poolWrapper.executeUpdate(SQL1);

		this.poolWrapper.executeUpdate(SQL2);

		return null;
	}

}
