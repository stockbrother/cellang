package org.cellang.corpsviewer;

import org.cellang.core.entity.CashFlowStatementReportEntity;
import org.cellang.viewsframework.ops.OperationContext;
import org.cellang.viewsframework.report.ReportTableView;

public class CashFlowReportView extends ReportTableView<CashFlowStatementReportEntity> {
	
	public CashFlowReportView(OperationContext oc, int years, String corpId) {
		super(oc, CashFlowStatementReportEntity.class, years, corpId);

	}

}
