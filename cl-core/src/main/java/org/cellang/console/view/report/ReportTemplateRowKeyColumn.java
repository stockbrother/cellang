package org.cellang.console.view.report;

import org.cellang.console.format.ReportItemLocator;
import org.cellang.console.format.ReportItemLocators;
import org.cellang.console.view.table.AbstractColumn;
import org.cellang.console.view.table.AbstractTableDataProvider;

public class ReportTemplateRowKeyColumn extends AbstractColumn<ReportTemplateRow> {

	ReportItemLocatorFilter filter;
	ReportItemLocators.Group template;
	public ReportTemplateRowKeyColumn(ReportItemLocators.Group template,AbstractTableDataProvider<ReportTemplateRow> model, ReportItemLocatorFilter filter) {
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

		ReportItemLocator ril = template.get(key);

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