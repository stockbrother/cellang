package org.cellang.console.corpgrouping;

import org.cellang.console.ops.OperationContext;
import org.cellang.console.view.report.ReportTableView;
import org.cellang.core.entity.CashFlowStatementReportEntity;

public class CashFlowReportView extends ReportTableView<CashFlowStatementReportEntity> {
	
	public CashFlowReportView(OperationContext oc, int years, String corpId) {
		super(oc, CashFlowStatementReportEntity.class, years, corpId);

	}

}
