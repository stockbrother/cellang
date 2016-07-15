package org.cellang.console.view;

import java.math.BigDecimal;

import org.cellang.console.chart.ChartSingleSerial;

public class CompoundGrowModel extends ChartSingleSerial<Integer> {

	private BigDecimal init;

	private BigDecimal growRatio;

	private int years;

	public CompoundGrowModel(BigDecimal init, BigDecimal growRatio, int years) {
		super("default");
		this.init = init;
		this.growRatio = growRatio;
		this.years = years;
	}

	@Override
	public int getXCount() {
		return this.years;
	}

	@Override
	public Integer getXValue(int idx) {
		return idx;
	}

	@Override
	public BigDecimal getYValue(Integer idx) {
		if (idx < 0 || idx > this.years) {
			return null;
		}
		BigDecimal rt = new BigDecimal(Math.pow(1 + growRatio.doubleValue(), idx));
		rt = rt.multiply(this.init);//
		return rt;
	}

}