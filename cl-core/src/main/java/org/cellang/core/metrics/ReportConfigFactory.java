package org.cellang.core.metrics;

import org.cellang.core.entity.BalanceSheetItemEntity;
import org.cellang.core.entity.BalanceSheetReportEntity;
import org.cellang.core.entity.EntityConfigFactory;
import org.cellang.core.entity.IncomeStatementItemEntity;
import org.cellang.core.entity.IncomeStatementReportEntity;

public class ReportConfigFactory {
	public ReportConfig balanceSheetReportConfig;
	public ReportConfig incomeStatementReportConfig;

	public ReportConfigFactory(EntityConfigFactory ecf) {
		this.balanceSheetReportConfig = new ReportConfig(ecf.get(BalanceSheetReportEntity.class),
				ecf.get(BalanceSheetItemEntity.class));
		this.incomeStatementReportConfig = new ReportConfig(ecf.get(IncomeStatementReportEntity.class),
				ecf.get(IncomeStatementItemEntity.class));

	}

}
