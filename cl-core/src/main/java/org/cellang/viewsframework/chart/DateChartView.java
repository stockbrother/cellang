package org.cellang.viewsframework.chart;

import org.cellang.core.util.ReportDate;
import org.cellang.viewsframework.ops.OperationContext;

public class DateChartView extends ChartView<ReportDate> {

	public DateChartView(String title, OperationContext oc, AbstractChartDataProvider<ReportDate> cd) {
		super(title, oc, cd);
	}

}
