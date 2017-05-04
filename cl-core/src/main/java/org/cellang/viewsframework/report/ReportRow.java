package org.cellang.viewsframework.report;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.cellang.core.entity.AbstractReportItemEntity;
import org.cellang.core.entity.EntityObject;
import org.cellang.corpsviewer.corpdata.ItemDefine;

public class ReportRow {
	List<AbstractReportItemEntity> itemList = new ArrayList<>();

	private String key;

	ItemDefine locator;

	int size;
	
	private ReportValueFilter valueFilter;

	public int getSize() {
		return size;
	}

	public String getKey() {
		return key;
	}

	public ReportRow(int size, String key, ItemDefine ri, ReportValueFilter valueFilter) {
		this.size = size;
		this.key = key;
		this.locator = ri;
		this.valueFilter = valueFilter;
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
		BigDecimal rt = en == null ? null : en.getValue();
		if(this.valueFilter != null){
			rt = this.valueFilter.getValue(year, rt, this);
		}
		return rt;
		
	}
}