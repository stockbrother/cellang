package org.cellang.corpsviewer;

import org.cellang.core.entity.IncomeStatementReportEntity;
import org.cellang.viewsframework.ops.OperationContext;
import org.cellang.viewsframework.report.ReportTableView;

public class IncomeStatementReportView extends ReportTableView<IncomeStatementReportEntity> {
	
	public IncomeStatementReportView(OperationContext oc, int years, String corpId) {
		super(oc, IncomeStatementReportEntity.class, years, corpId);

	}

}
