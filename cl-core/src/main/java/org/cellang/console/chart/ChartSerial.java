package org.cellang.console.chart;

import java.math.BigDecimal;

public abstract class ChartSerial<T> extends ChartWindow<T> {
	protected String name;

	public ChartSerial(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public abstract BigDecimal getYValue(T xValue);

	@Override
	protected BigDecimal[] doGetActualMinMax() {
		BigDecimal min = null;
		BigDecimal max = null;

		int count = this.getWindowSize();

		for (int i = 0; i < count; i++) {
			T xValue = this.getXValue(i);

			BigDecimal score = this.getYValue(xValue);
			if (score == null) {
				continue;
			}
			if (min == null) {
				min = score;
			}
			if (max == null) {
				max = score;
			}

			min = min.min(score);
			max = max.max(score);

		}
		return new BigDecimal[] { min, max };
	}
}
