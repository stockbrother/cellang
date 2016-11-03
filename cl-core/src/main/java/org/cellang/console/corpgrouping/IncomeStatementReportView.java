package org.cellang.console.corpgrouping;

import org.cellang.console.ops.OperationContext;
import org.cellang.console.view.report.ReportTableView;
import org.cellang.core.entity.IncomeStatementReportEntity;

public class IncomeStatementReportView extends ReportTableView<IncomeStatementReportEntity> {
	
	public IncomeStatementReportView(OperationContext oc, int years, String corpId) {
		super(oc, IncomeStatementReportEntity.class, years, corpId);

	}

}
