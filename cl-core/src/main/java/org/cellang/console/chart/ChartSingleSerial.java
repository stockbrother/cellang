package org.cellang.console.chart;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public abstract class ChartSingleSerial<T> extends ChartSerial<T> {
	protected String name;

	public ChartSingleSerial(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public BigDecimal getYValue(String name, T xValue) {
		return this.getYValue(xValue);
	}

	public abstract BigDecimal getYValue(T xValue);

	@Override
	public List<String> getSerialNameList() {
		List<String> rt = new ArrayList<String>();
		rt.add(name);
		return rt;
	}

}
