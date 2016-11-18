package org.cellang.viewsframework.view.report;

import java.util.Date;

import org.cellang.viewsframework.ops.OperationContext;
import org.cellang.viewsframework.view.chart.ChartView;

public class ReportRowChartView extends ChartView<Date> {

	public ReportRowChartView(OperationContext oc, ReportRowChartDataProvider dp) {
		super("ReportRow", oc, dp);
	}

}
