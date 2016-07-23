package org.cellang.console.view.chart;

import org.cellang.console.chart.ChartModel;
import org.cellang.core.util.ReportDate;

public class ReportItemChartModel extends ChartModel<ReportDate> {


	@Override
	public String getXDisplayValue(ReportDate xValue) {
		return xValue.format();
	}

}
