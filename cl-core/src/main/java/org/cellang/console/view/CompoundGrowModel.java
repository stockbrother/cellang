package org.cellang.console.view;

import java.math.BigDecimal;

import org.cellang.console.chart.ChartModel;

public class CompoundGrowModel extends ChartModel {

	private BigDecimal init;

	private BigDecimal growRatio;

	private int years;

	public CompoundGrowModel(BigDecimal init, BigDecimal growRatio, int years) {
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
	public BigDecimal getYValue(int idx) {
		BigDecimal rt = new BigDecimal(Math.pow(1 + growRatio.doubleValue(), idx));
		rt = rt.multiply(this.init);//
		return rt;
	}

}