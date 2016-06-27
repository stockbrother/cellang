package org.cellang.core.entity;

import java.util.Date;

public class DateInfoEntity extends EntityObject {

	public static String tableName = "dateinfo";

	private Date value;

	public Date getValue() {
		return value;
	}

	public void setValue(Date value) {
		this.value = value;
	}
}
