package org.cellang.core.entity;

import java.util.Date;

public class CorpMetricEntity extends EntityObject {

	public static final String tableName = "corpmetric";
	
	private String corpId;

	private Date reportDate;
	
	private String key;

	private Double value;

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

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

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
}
