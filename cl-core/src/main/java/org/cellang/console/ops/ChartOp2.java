
package org.cellang.console.ops;

import java.util.Date;

import org.cellang.console.view.AbstractChartDataProvider;
import org.cellang.console.view.ChartView;
import org.cellang.console.view.DateChartView;
import org.cellang.console.view.ReportDate;
import org.cellang.console.view.ReportItemChartDataProvider;
import org.cellang.console.view.ViewsPane;
import org.cellang.core.entity.BalanceSheetItemEntity;
import org.cellang.core.entity.BalanceSheetReportEntity;
import org.cellang.core.entity.EntityConfig;
import org.cellang.core.entity.IncomeStatementItemEntity;
import org.cellang.core.entity.IncomeStatementReportEntity;

public class ChartOp2 extends ConsoleOp<Void> {

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
		ViewsPane views = oc.getViewManager();
		EntityConfig ec = oc.getSelectedEntityConfig();
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

		DateChartView view = new DateChartView("ChartView", dp);

		views.addView(view, true);// TODO Auto-generated method stub
		return null;
	}
}