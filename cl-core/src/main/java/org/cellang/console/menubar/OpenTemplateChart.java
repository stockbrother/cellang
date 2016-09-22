package org.cellang.console.menubar;

import org.cellang.console.control.Action;
import org.cellang.console.ops.OperationContext;
import org.cellang.console.view.report.ReportTemplateRow;
import org.cellang.console.view.report.ReportTemplateRowChartDataProvider;
import org.cellang.console.view.report.ReportTemplateRowChartView;

public class OpenTemplateChart extends Action {
	OperationContext oc;

	OpenTemplateChart(OperationContext oc) {
		this.oc = oc;
	}

	@Override
	public String getName() {
		return "Template Chart";
	}

	@Override
	public void perform() {
		ReportTemplateRow rtr = oc.getReportTemplateRow();
		ReportTemplateRowChartDataProvider chartDp = new ReportTemplateRowChartDataProvider(oc);
		chartDp.addReportRow(rtr);
		ReportTemplateRowChartView cv = new ReportTemplateRowChartView(oc, chartDp);
		oc.getViewManager().addView(2, cv, true);

	}
}