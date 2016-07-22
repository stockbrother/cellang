package org.cellang.console.model;

import org.cellang.console.chart.ChartModel;
import org.cellang.console.view.ReportDate;

public class ReportItemChartModel extends ChartModel<ReportDate> {


	@Override
	public String getXDisplayValue(ReportDate xValue) {
		return xValue.format();
	}

}
