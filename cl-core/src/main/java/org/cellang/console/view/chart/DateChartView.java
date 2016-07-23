package org.cellang.console.view.chart;

import org.cellang.core.util.ReportDate;

public class DateChartView extends ChartView<ReportDate> {

	public DateChartView(String title, AbstractChartDataProvider<ReportDate> cd) {
		super(title, cd);
	}

}
