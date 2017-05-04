package org.cellang.viewsframework.report;

import org.cellang.corpsviewer.corpdata.ItemDefine;
import org.cellang.corpsviewer.corpdata.ItemDefines;
import org.cellang.viewsframework.table.AbstractColumn;
import org.cellang.viewsframework.table.AbstractTableDataProvider;

public class ReportTemplateRowKeyColumn extends AbstractColumn<ReportTemplateRow> {

	ReportItemLocatorFilter filter;
	ItemDefines.Group template;
	public ReportTemplateRowKeyColumn(ItemDefines.Group template,AbstractTableDataProvider<ReportTemplateRow> model, ReportItemLocatorFilter filter) {
		super(model, "Key");
		this.template = template;
		this.filter = filter;
	}

	@Override
	public Object getValue(int rowIndex, ReportTemplateRow ec) {
		String key = ec.getKey();
		String rt = key;
		if (filter.collapsedKey.contains(ec.getKey())) {
			rt = "+)" + rt;
		} else {
			rt = "(+" + rt;
		}

		ItemDefine ril = template.get(key);

		int dep = 0;

		while (ril != null) {
			ril = ril.getParent();
			if (dep > 0) {
				rt = "  " + rt;
			}

			dep++;
		}
		return "" + rt;
	}
}