package org.cellang.core.entity;

import java.math.BigDecimal;

public class AbstractReportItemEntity extends EntityObject {
	
	private String reportId;
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

	public String getReportId() {
		return reportId;
	}

	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

}
