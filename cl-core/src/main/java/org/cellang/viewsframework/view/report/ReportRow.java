package org.cellang.viewsframework.view.report;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.cellang.core.entity.AbstractReportItemEntity;
import org.cellang.core.entity.EntityObject;
import org.cellang.viewsframework.format.ReportItemLocator;

public class ReportRow {
	List<AbstractReportItemEntity> itemList = new ArrayList<>();

	private String key;

	ReportItemLocator locator;

	int size;

	public int getSize() {
		return size;
	}

	public String getKey() {
		return key;
	}

	public ReportRow(int size, String key, ReportItemLocator ri) {
		this.size = size;
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

	public BigDecimal getValue(int year) {
		//
		AbstractReportItemEntity en = this.get(year);
		return en == null ? null : en.getValue();
	}
}