package org.cellang.console.view.report;

import org.cellang.console.format.ReportItemLocator;
import org.cellang.core.metrics.ReportConfig;

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