package org.cellang.console.view;

import org.cellang.console.model.AbstractChartDataProvider;

public class DateChartView extends ChartView<ReportDate> {

	public DateChartView(String title, AbstractChartDataProvider<ReportDate> cd) {
		super(title, cd);
	}

}
