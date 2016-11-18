
package org.cellang.viewsframework.ops;

import org.cellang.core.entity.BalanceSheetItemEntity;
import org.cellang.core.entity.BalanceSheetReportEntity;
import org.cellang.core.entity.EntityConfig;
import org.cellang.core.entity.IncomeStatementItemEntity;
import org.cellang.core.entity.IncomeStatementReportEntity;
import org.cellang.core.util.ReportDate;
import org.cellang.viewsframework.PerspectivePanel;
import org.cellang.viewsframework.view.chart.AbstractChartDataProvider;
import org.cellang.viewsframework.view.chart.DateChartView;
import org.cellang.viewsframework.view.chart.ReportItemChartDataProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChartOp2 extends ConsoleOp<Void> {
	private static final Logger LOG = LoggerFactory.getLogger(ChartOp2.class);

	private OperationContext oc;
	String[] corpIdArray;
	String itemKey;
	int pageSize;

	public ChartOp2(OperationContext oc) {
		this.oc = oc;
	}

	public ChartOp2 set(String[] corpIdArray, String itemKey, int pageSize) {
		this.corpIdArray = corpIdArray;
		this.itemKey = itemKey;
		this.pageSize = pageSize;
		return this;
	}

	@Override
	public Void execute(OperationContext oc) {
		PerspectivePanel views = oc.getViewManager();
		EntityConfig ec = null;//oc.getEntityConfigManager().getSelectedEntityConfig();
		EntityConfig ecItem = null;
		if (BalanceSheetReportEntity.class.equals(ec.getEntityClass())) {
			ecItem = oc.getEntityConfigFactory().get(BalanceSheetItemEntity.class);
		} else if (IncomeStatementReportEntity.class.equals(ec.getEntityClass())) {
			ecItem = oc.getEntityConfigFactory().get(IncomeStatementItemEntity.class);
		} else {
			throw new RuntimeException("not supported:" + ec.getEntityClass());
		}
		AbstractChartDataProvider<ReportDate> dp = new ReportItemChartDataProvider(this.pageSize, oc.getEntityService(),
				this.corpIdArray, this.itemKey, ec, ecItem, ReportDate.valueOf(2016));

		DateChartView view = new DateChartView("ChartView",oc, dp);

		views.addView(view, true);// TODO Auto-generated method stub
		return null;
	}
}