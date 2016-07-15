package org.cellang.console.view;

import org.cellang.console.chart.ChartModel;

public class ReportItemChartModel extends ChartModel<ReportDate> {


	@Override
	public String getXDisplayValue(ReportDate xValue) {
		return xValue.format();
	}

}
