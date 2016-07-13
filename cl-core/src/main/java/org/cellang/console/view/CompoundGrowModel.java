package org.cellang.console.view;

import org.cellang.console.chart.ChartModel;

public class CompoundGrowModel extends ChartModel {

	private double init;

	private double growRatio;

	private int years;

	public CompoundGrowModel(double init, double growRatio, int years) {
		this.init = init;
		this.growRatio = growRatio;
		this.years = years;
	}

	@Override
	public int getCount() {
		return this.years;
	}

	@Override
	public String getXValue(int idx) {
		return String.valueOf(idx);
	}

	@Override
	public Double getYValue(int idx) {
		double rt = Math.pow(1 + growRatio, idx);
		rt = rt * this.init;
		return rt;
	}

}