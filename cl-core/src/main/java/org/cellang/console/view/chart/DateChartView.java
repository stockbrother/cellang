package org.cellang.console.view.chart;

import org.cellang.console.ops.OperationContext;
import org.cellang.core.util.ReportDate;

public class DateChartView extends ChartView<ReportDate> {

	public DateChartView(String title, OperationContext oc, AbstractChartDataProvider<ReportDate> cd) {
		super(title, oc, cd);
	}

}
