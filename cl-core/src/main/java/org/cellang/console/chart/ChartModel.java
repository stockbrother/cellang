package org.cellang.console.chart;

public abstract class ChartModel {
	protected Double min;

	protected Double max;

	public abstract int getCount();

	public abstract String getXValue(int idx);

	public abstract Double getYValue(int idx);

	public Double getMin() {

		if (this.min == null) {
			this.updateMinMax();
		}
		return this.min.doubleValue();
	}

	public Double getMax() {

		if (this.max == null) {
			this.updateMinMax();
		}
		return this.max.doubleValue();
	}

	protected void updateMinMax() {
		double min = Double.MAX_VALUE;
		double max = Double.MIN_VALUE;

		int count = this.getCount();
		for (int i = 0; i < count; i++) {
			Double score = this.getYValue(i);
			min = Math.min(min, score);
			max = Math.max(max, score);
		}
		this.min = min;
		this.max = max;
	}

}
