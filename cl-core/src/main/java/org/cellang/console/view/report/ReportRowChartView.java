package org.cellang.console.view.report;

import java.util.Date;

import org.cellang.console.view.chart.ChartView;

public class ReportRowChartView extends ChartView<Date> {

	public ReportRowChartView(ReportRowChartDataProvider dp) {
		super("ReportRow", dp);
	}

}
