package org.cellang.console.chart;

import java.math.BigDecimal;
import java.util.List;

public class SimpleChartModel extends ChartSingleSerial<Integer> {
	private List<BigDecimal> valueList;

	SimpleChartModel(List<BigDecimal> valueList) {
		super("default");
		this.valueList = valueList;
	}

	@Override
	public int getXCount() {
		return this.valueList.size();
	}

	@Override
	public BigDecimal getYValue(Integer idx) {
		if (idx < 0 || idx >= this.valueList.size()) {
			return null;
		}
		return this.valueList.get(idx);
	}

	@Override
	public Integer getXValue(int idx) {
		return idx;
	}


	@Override
	public void clearPoints() {
		this.valueList.clear();
	}
}
