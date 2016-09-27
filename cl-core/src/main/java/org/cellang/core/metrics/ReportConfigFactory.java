package org.cellang.core.metrics;

import java.util.HashMap;
import java.util.Map;

import org.cellang.core.entity.AbstractReportEntity;
import org.cellang.core.entity.BalanceSheetItemEntity;
import org.cellang.core.entity.BalanceSheetReportEntity;
import org.cellang.core.entity.CashFlowStatementItemEntity;
import org.cellang.core.entity.CashFlowStatementReportEntity;
import org.cellang.core.entity.CustomizedItemEntity;
import org.cellang.core.entity.CustomizedReportEntity;
import org.cellang.core.entity.EntityConfig;
import org.cellang.core.entity.EntityConfigFactory;
import org.cellang.core.entity.IncomeStatementItemEntity;
import org.cellang.core.entity.IncomeStatementReportEntity;

public class ReportConfigFactory {

	private Map<Class, ReportConfig> reportConfigMap = new HashMap<>();

	public ReportConfigFactory(EntityConfigFactory ecf) {
		add(ecf, BalanceSheetReportEntity.class, BalanceSheetItemEntity.class);

		add(ecf, IncomeStatementReportEntity.class, IncomeStatementItemEntity.class);

		add(ecf, CashFlowStatementReportEntity.class, CashFlowStatementItemEntity.class);

		add(ecf, CustomizedReportEntity.class, CustomizedItemEntity.class);

	}
	
	public <T extends AbstractReportEntity> ReportConfig get(Class<T> cls){
		return this.reportConfigMap.get(cls);
	}

	private void add(EntityConfigFactory ecf, Class cls1, Class cls2) {
		EntityConfig e1 = ecf.get(cls1);
		EntityConfig e2 = ecf.get(cls2);
		this.reportConfigMap.put(cls1, new ReportConfig(e1, e2));
	}

}
