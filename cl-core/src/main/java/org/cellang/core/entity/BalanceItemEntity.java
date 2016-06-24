package org.cellang.core.entity;

import java.math.BigDecimal;

import org.cellang.commons.jdbc.CreateTableOperation;
import org.cellang.commons.jdbc.InsertRowOperation;

public class BalanceItemEntity extends EntityObject {
	public static final String tableName = "balanceitem";
	
	private String balanceSheetId;
	private String key;
	private BigDecimal value;

	public String getKey() {
		return key;
	}

	public void setKey(String name) {
		this.key = name;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public String getBalanceSheetId() {
		return balanceSheetId;
	}

	public void setBalanceSheetId(String balanceSheetId) {
		this.balanceSheetId = balanceSheetId;
	}

	public void fillInsert(InsertRowOperation insert) {
		insert.addValue("id", this.id);//
		insert.addValue("balanceSheetId", this.balanceSheetId);
		insert.addValue("name", this.key);
		insert.addValue("value", this.value);
	}

	public static void fillCreate(CreateTableOperation cto) {
		cto.addColumn("id", String.class);
		cto.addColumn("balanceSheetId", String.class);
		cto.addColumn("name", String.class);
		cto.addColumn("value", BigDecimal.class);
		cto.addPrimaryKey("id");//

	}
}
