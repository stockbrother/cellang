package org.cellang.core.metrics;

import org.cellang.core.entity.BalanceSheetItemEntity;
import org.cellang.core.entity.BalanceSheetReportEntity;
import org.cellang.core.entity.CashFlowStatementItemEntity;
import org.cellang.core.entity.CashFlowStatementReportEntity;
import org.cellang.core.entity.CustomizedItemEntity;
import org.cellang.core.entity.CustomizedReportEntity;
import org.cellang.core.entity.EntityConfigFactory;
import org.cellang.core.entity.IncomeStatementItemEntity;
import org.cellang.core.entity.IncomeStatementReportEntity;

public class ReportConfigFactory {
	public ReportConfig balanceSheetReportConfig;
	public ReportConfig incomeStatementReportConfig;
	public ReportConfig customizedReportConfig;
	public ReportConfig cashFlowStatementReportConfig;

	public ReportConfigFactory(EntityConfigFactory ecf) {
		this.balanceSheetReportConfig = new ReportConfig(ecf.get(BalanceSheetReportEntity.class),
				ecf.get(BalanceSheetItemEntity.class));

		this.incomeStatementReportConfig = new ReportConfig(ecf.get(IncomeStatementReportEntity.class),
				ecf.get(IncomeStatementItemEntity.class));

		this.cashFlowStatementReportConfig = new ReportConfig(ecf.get(CashFlowStatementReportEntity.class),
				ecf.get(CashFlowStatementItemEntity.class));

		this.customizedReportConfig = new ReportConfig(ecf.get(CustomizedReportEntity.class),
				ecf.get(CustomizedItemEntity.class));

	}

}
