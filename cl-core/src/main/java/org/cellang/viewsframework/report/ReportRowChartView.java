package org.cellang.viewsframework.report;

import java.util.Date;

import org.cellang.viewsframework.chart.ChartView;
import org.cellang.viewsframework.ops.OperationContext;

public class ReportRowChartView extends ChartView<Date> {

	public ReportRowChartView(OperationContext oc, ReportRowChartDataProvider dp) {
		super("ReportRow", oc, dp);
	}

}
