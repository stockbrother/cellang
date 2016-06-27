package org.cellang.core.entity;

import java.math.BigDecimal;

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

}
