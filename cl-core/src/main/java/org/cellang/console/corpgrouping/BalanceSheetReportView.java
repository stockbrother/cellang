package org.cellang.console.corpgrouping;

import org.cellang.console.ops.OperationContext;
import org.cellang.console.view.report.ReportTableView;
import org.cellang.core.entity.BalanceSheetReportEntity;

public class BalanceSheetReportView extends ReportTableView<BalanceSheetReportEntity> {
	
	public BalanceSheetReportView(OperationContext oc, int years, String corpId) {
		super(oc, BalanceSheetReportEntity.class, years, corpId);

	}

}
