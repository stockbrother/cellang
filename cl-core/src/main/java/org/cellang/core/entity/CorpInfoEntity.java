package org.cellang.core.entity;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.cellang.commons.jdbc.CreateTableOperation;
import org.cellang.commons.jdbc.InsertRowOperation;

public class CorpInfoEntity extends EntityObject {

	public static String tableName = "corpinfo";

	private String code;

	private String name;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void fillInsert(InsertRowOperation insert) {
		insert.addValue("id", this.id);//
		insert.addValue("code", code);
		insert.addValue("name", name);
	}

	public void extractFrom(ResultSet rs) throws SQLException {
		this.id = rs.getString("id");//
		this.code = rs.getString("code");
		this.name = rs.getString("name");
	}

	public static void fillCreate(CreateTableOperation cto) {
		cto.addColumn("id", String.class);
		cto.addColumn("code", String.class);
		cto.addColumn("name", String.class);
		cto.addPrimaryKey("id");//

	}
}
