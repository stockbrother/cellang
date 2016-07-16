package org.cellang.console.view;

import java.math.BigDecimal;

import org.cellang.console.chart.ChartSerial;

public class CompoundGrowModel extends ChartSerial<Integer> {

	private BigDecimal init;

	private BigDecimal growRatio;

	private int size;
	
	private int offset = 0;

	public CompoundGrowModel(BigDecimal init, BigDecimal growRatio, int years) {
		super("default");
		this.init = init;
		this.growRatio = growRatio;
		this.size = years;
	}

	@Override
	public int getWindowSize() {
		return this.size;
	}

	@Override
	public Integer getXValue(int idx) {		
		return this.offset + idx;
	}

	@Override
	public BigDecimal getYValue(Integer idx) {
		
		if (idx < 0 || idx > this.size) {
			return null;
		}
		BigDecimal rt = new BigDecimal(Math.pow(1 + growRatio.doubleValue(), idx));
		rt = rt.multiply(this.init);//
		return rt;
	}
	

	@Override
	public void moveWindowTo(Integer offset) {
		//not support
		this.offset = offset;
	}

}