package org.cellang.viewsframework.report;

import org.cellang.core.metrics.ReportConfig;
import org.cellang.viewsframework.format.ReportItemLocator;

public class ReportTemplateRow {
	
	ReportConfig reportConfig;
	public ReportConfig getReportConfig() {
		return reportConfig;
	}

	private String key;

	ReportItemLocator locator;

	public String getKey() {
		return key;
	}

	public ReportTemplateRow(ReportConfig rc, String key, ReportItemLocator ri) {
		this.reportConfig = rc;
		this.key = key;
		this.locator = ri;
	}

}