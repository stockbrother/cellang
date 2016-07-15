package org.cellang.console.chart;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class SimpleChartModel extends ChartSingleSerial<Integer> {
	private Integer offset = 0;
	private int windowSize = 10;
	private List<BigDecimal> valueList = new ArrayList<BigDecimal>();

	SimpleChartModel() {
		super("default");
	}

	@Override
	public int getWindowSize() {
		return this.windowSize;
	}

	@Override
	public BigDecimal getYValue(Integer idx2) {
		if (idx2 < 0 || idx2 >= this.valueList.size()) {
			return null;
		}
		return this.valueList.get(idx2);
	}

	@Override
	public Integer getXValue(int idx) {

		return idx + offset;
	}

	@Override
	public void moveWindowTo(Integer start) {
		this.offset = start;
	}
}
