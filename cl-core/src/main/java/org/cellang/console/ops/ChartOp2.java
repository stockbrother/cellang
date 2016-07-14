package org.cellang.console.ops;

import java.util.Date;

import org.cellang.console.view.AbstractChartDataProvider;
import org.cellang.console.view.ChartView;
import org.cellang.console.view.ReportItemChartDataProvider;
import org.cellang.console.view.ViewsPane;
import org.cellang.core.entity.BalanceSheetItemEntity;
import org.cellang.core.entity.BalanceSheetReportEntity;
import org.cellang.core.entity.EntityConfig;
import org.cellang.core.entity.IncomeStatementItemEntity;
import org.cellang.core.entity.IncomeStatementReportEntity;

public class ChartOp2 extends ConsoleOp<Void> {

	private OperationContext oc;
	String corpId;
	String itemKey;

	public ChartOp2(OperationContext oc) {
		this.oc = oc;
	}

	public ChartOp2 set(String corpId, String itemKey) {
		this.corpId = corpId;
		this.itemKey = itemKey;
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
		AbstractChartDataProvider dp = new ReportItemChartDataProvider(10, oc.getEntityService(), this.corpId,
				this.itemKey, ec, ecItem, new Date(2016 - 1900, 12, 31).getTime());
		ChartView view = new ChartView("ChartView", dp);

		views.addView(view, true);// TODO Auto-generated method stub
		return null;
	}
}
