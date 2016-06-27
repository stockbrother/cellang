package org.cellang.core.entity;

import java.util.Date;

public abstract class AbstractReportEntity extends EntityObject {
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
