package org.cellang.viewsframework.report;

import org.cellang.core.metrics.ReportConfig;
import org.cellang.corpsviewer.corpdata.ItemDefine;

public class ReportTemplateRow {
	
	ReportConfig reportConfig;
	public ReportConfig getReportConfig() {
		return reportConfig;
	}

	private String key;

	ItemDefine locator;

	public String getKey() {
		return key;
	}

	public ReportTemplateRow(ReportConfig rc, String key, ItemDefine ri) {
		this.reportConfig = rc;
		this.key = key;
		this.locator = ri;
	}

}