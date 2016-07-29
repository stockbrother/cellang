package org.cellang.console.view.report;

import org.cellang.console.format.ReportItemLocator;
import org.cellang.console.view.table.AbstractColumn;
import org.cellang.console.view.table.AbstractTableDataProvider;

public class ReportRowKeyColumn extends AbstractColumn<ReportRow> {

	ReportItemLocatorFilter filter;

	public ReportRowKeyColumn(AbstractTableDataProvider<ReportRow> model, ReportItemLocatorFilter filter) {
		super(model, "key");
		this.filter = filter;
	}

	@Override
	public Object getValue(int rowIndex, ReportRow ec) {
		String key = ec.getKey();
		String rt = key;
		if (filter.collapsedKey.contains(ec.getKey())) {
			rt = "+)" + rt;
		} else {
			rt = "(+" + rt;
		}

		ReportItemLocator ril = ReportTableDataProvider.RIL.get(key);

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