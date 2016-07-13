package org.cellang.console.chart;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicReference;

public abstract class ChartModel {
	protected AtomicReference<BigDecimal> min;

	protected AtomicReference<BigDecimal> max;

	public abstract int getCount();

	public abstract String getXValue(int idx);

	public abstract BigDecimal getYValue(int idx);

	public BigDecimal getMin() {

		if (this.min == null) {
			this.calculateMinMax();
		}
		return this.min.get();
	}

	public BigDecimal getMax() {

		if (this.max == null) {
			this.calculateMinMax();
		}
		return this.max.get();
	}

	protected void calculateMinMax() {
		BigDecimal min = null;
		BigDecimal max = null;

		int count = this.getCount();
		for (int i = 0; i < count; i++) {
			BigDecimal score = this.getYValue(i);

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
		this.min = new AtomicReference<BigDecimal>(min);
		this.max = new AtomicReference<BigDecimal>(max);
	}

	public void invalidCache() {
		max = null;
		min = null;
	}

}
