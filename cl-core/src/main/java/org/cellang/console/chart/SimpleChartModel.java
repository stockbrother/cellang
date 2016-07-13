package org.cellang.console.chart;

import java.math.BigDecimal;
import java.util.List;

public class SimpleChartModel extends ChartModel {
	private List<BigDecimal> valueList;

	SimpleChartModel(List<BigDecimal> valueList) {
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
	public BigDecimal getYValue(int idx) {
		return this.valueList.get(idx);
	}

}
