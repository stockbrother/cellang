package org.cellang.commons.jdbc;

import java.util.ArrayList;
import java.util.List;

public class InsertRowOperation extends JdbcOperation<Void> {

	private String tableName;

	private List<String> columnList = new ArrayList<String>();

	private List<Object> valueList = new ArrayList<Object>();

	public InsertRowOperation(ConnectionPoolWrapper cpw, String table) {
		super(cpw);
		this.tableName = table;
	}

	public void addValue(String name, Object value) {
		this.columnList.add(name);//
		this.valueList.add(value);
	}

	@Override
	public Void execute() {
		//TODO static sql.
		StringBuffer sql = new StringBuffer().append("insert into ").append(this.tableName).append(" (");
		for (int i = 0; i < valueList.size(); i++) {
			String name = columnList.get(i);
			sql.append(name);
			if (i < valueList.size() - 1) {
				sql.append(",");
			}
		}
		sql.append(")values(");
		for (int i = 0; i < valueList.size(); i++) {
			sql.append("?");
			if (i < valueList.size() - 1) {
				sql.append(",");
			}
		}
		sql.append(")");

		this.poolWrapper.executeUpdate(sql.toString(), valueList);
		return null;
	}

}
