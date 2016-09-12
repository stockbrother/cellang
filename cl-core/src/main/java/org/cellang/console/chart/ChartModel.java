package org.cellang.console.chart;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChartModel<T> extends ChartWindow<T> {

	private List<ChartSerial<T>> serialList = new ArrayList<>();

	private Map<String, ChartSerial<T>> serialMap = new HashMap<>();

	private int defaultWindowSize;

	public ChartModel() {
		this(2);
	}

	public ChartModel(int defaultWindowSize) {
		this.defaultWindowSize = defaultWindowSize;
	}

	public ChartModel(ChartSerial<T> css) {
		this(css.getWindowSize());
		this.addSerail(css);
	}

	public ChartSerial<T> getSerial(String sname) {
		return serialMap.get(sname);
	}
	
	public void clearSerials(){
		this.serialMap.clear();
		this.serialList.clear();
	}

	public void addSerail(ChartSerial<T> css) {
		String key = css.getName();
		if (this.serialMap.containsKey(key)) {
			throw new RuntimeException("duplicated:" + key);
		}
		this.serialList.add(css);
		ChartSerial<T> old = this.serialMap.put(key, css);
	}

	@Override
	public int getWindowSize() {
		if (this.serialMap.isEmpty()) {
			return this.defaultWindowSize;
		}
		return this.serialMap.values().iterator().next().getWindowSize();
	}

	public List<ChartSerial<T>> getSerialList() {
		List<ChartSerial<T>> rt = new ArrayList<>();
		rt.addAll(this.serialList);
		return rt;

	}

	@Override
	public T getXValue(int idx) {
		if (this.serialMap.isEmpty()) {
			return null;
		}
		return this.serialMap.values().iterator().next().getXValue(idx);
	}

	public BigDecimal getYValue(String name, T xValue) {
		return this.getSerial(name).getYValue(xValue);
	}

	@Override
	public void moveWindowTo(T startXValue) {
		for (ChartWindow<T> cs : this.serialMap.values()) {
			cs.moveWindowTo(startXValue);//
		}
	}

	@Override
	protected BigDecimal[] doGetActualMinMax() {
		BigDecimal min = null;
		BigDecimal max = null;

		for (int j = 0; j < this.serialList.size(); j++) {
			BigDecimal[] mm = this.serialList.get(j).doGetActualMinMax();
			if (min == null || min.compareTo(mm[0]) > 0) {
				min = mm[0];
			}
			if (max == null || max.compareTo(mm[1]) < 0) {
				max = mm[1];
			}
		}

		return new BigDecimal[] { min, max };
	}

}
