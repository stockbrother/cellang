package org.cellang.corpsviewer;

import org.cellang.core.entity.BalanceSheetReportEntity;
import org.cellang.viewsframework.ops.OperationContext;
import org.cellang.viewsframework.view.report.ReportTableView;

public class BalanceSheetReportView extends ReportTableView<BalanceSheetReportEntity> {

	public BalanceSheetReportView(OperationContext oc, int years, String corpId) {
		super(oc, BalanceSheetReportEntity.class, years, corpId, new BalanceSheetReportValueFilter());

	}

}
