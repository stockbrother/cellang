package org.cellang.console.view.report;

import org.cellang.console.format.ReportItemLocator;
import org.cellang.console.view.table.AbstractColumn;
import org.cellang.console.view.table.AbstractTableDataProvider;

public class ReportRowKeyColumn extends AbstractColumn<ReportRow> {

	public ReportRowKeyColumn(AbstractTableDataProvider<ReportRow> model) {
		super(model, "key");
	}

	@Override
	public Object getValue(int rowIndex, ReportRow ec) {
		String key = ec.getKey();
		ReportItemLocator ril = ReportTableDataProvider.RIL.get(key);
		int dep = 0;
		while (ril != null) {
			ril = ril.getParent();
			if (dep > 0) {
				key = "| " + key;
			}

			dep++;
		}
		return key;
	}
}