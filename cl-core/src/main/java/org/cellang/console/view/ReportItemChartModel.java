package org.cellang.console.view;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.cellang.console.chart.ChartModel;

public class ReportItemChartModel extends ChartModel<Long> {

	private static final SimpleDateFormat DF = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	public String getXDisplayValue(Long xValue) {
		return DF.format(new Date(xValue.longValue()));
	}

}
