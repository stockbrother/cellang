package org.cellang.core.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.cellang.commons.jdbc.CreateTableOperation;
import org.cellang.commons.jdbc.InsertRowOperation;

public class DateInfoEntity extends EntityObject {

	public static String tableName = "dateinfo";

	private Date value;

	public static void fillCreate(CreateTableOperation cto) {
		cto.addColumn("id", String.class);
		cto.addColumn("value", Date.class);
		cto.addPrimaryKey("id");//

	}

	public Date getValue() {
		return value;
	}

	public void setValue(Date value) {
		this.value = value;
	}
}
