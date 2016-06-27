package org.cellang.core.entity;

import java.util.Date;

public class BalanceSheetEntity extends EntityObject {
	public static final String tableName = "balancesheet";
	private String corpId;
	private Date reportDate;
	
	public String getCorpId() {
		return corpId;
	}

	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}

	public Date getReportDate() {
		return reportDate;
	}

	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}
	
}
