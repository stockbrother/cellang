package org.cellang.corpsviewer.actions;

import org.cellang.viewsframework.control.Action;
import org.cellang.viewsframework.ops.OperationContext;
import org.cellang.viewsframework.view.report.ReportTemplateRow;
import org.cellang.viewsframework.view.report.ReportTemplateRowChartDataProvider;
import org.cellang.viewsframework.view.report.ReportTemplateRowChartView;

public class OpenTemplateChart extends Action {
	OperationContext oc;

	public OpenTemplateChart(OperationContext oc) {
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