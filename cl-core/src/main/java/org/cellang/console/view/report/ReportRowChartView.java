package org.cellang.console.view.report;

import java.util.Date;

import org.cellang.console.ops.OperationContext;
import org.cellang.console.view.chart.ChartView;

public class ReportRowChartView extends ChartView<Date> {

	public ReportRowChartView(OperationContext oc, ReportRowChartDataProvider dp) {
		super("ReportRow", oc, dp);
	}

}
