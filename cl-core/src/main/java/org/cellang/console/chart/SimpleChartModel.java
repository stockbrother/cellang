package org.cellang.console.chart;

import java.util.List;

public class SimpleChartModel extends ChartModel {
	private List<Double> valueList;

	SimpleChartModel(List<Double> valueList) {
		this.valueList = valueList;
	}

	@Override
	public int getCount() {
		return this.valueList.size();
	}

	@Override
	public String getXValue(int idx) {

		return String.valueOf(idx);
	}

	@Override
	public Double getYValue(int idx) {
		return this.valueList.get(idx);
	}

}
