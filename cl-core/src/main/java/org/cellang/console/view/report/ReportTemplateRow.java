package org.cellang.console.view.report;

import java.util.ArrayList;
import java.util.List;

import org.cellang.console.format.ReportItemLocator;
import org.cellang.core.entity.AbstractReportItemEntity;

public class ReportTemplateRow {
	List<AbstractReportItemEntity> itemList = new ArrayList<>();

	private String key;

	ReportItemLocator locator;

	public String getKey() {
		return key;
	}

	public ReportTemplateRow(String key, ReportItemLocator ri) {
		this.key = key;
		this.locator = ri;
	}

}