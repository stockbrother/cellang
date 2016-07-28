package org.cellang.console.view.report;

import java.util.ArrayList;
import java.util.List;

import org.cellang.console.format.ReportItemLocator;
import org.cellang.core.entity.AbstractReportItemEntity;
import org.cellang.core.entity.EntityObject;

public class ReportRow {
	List<AbstractReportItemEntity> itemList = new ArrayList<>();

	private String key;

	ReportItemLocator locator;

	public String getKey() {
		return key;
	}

	public ReportRow(String key, ReportItemLocator ri) {
		this.key = key;
		this.locator = ri;
	}

	public AbstractReportItemEntity get(int idx) {
		if (idx >= this.itemList.size()) {
			return null;
		}
		return itemList.get(idx);
	}

	public EntityObject getFirstNotNullItem() {
		for (EntityObject eo : this.itemList) {
			if (eo != null) {
				return eo;
			}
		}
		return null;
	}

	public void set(int idx, AbstractReportItemEntity eo) {
		while (idx >= itemList.size()) {
			itemList.add(null);
		}
		itemList.set(idx, eo);
	}
}