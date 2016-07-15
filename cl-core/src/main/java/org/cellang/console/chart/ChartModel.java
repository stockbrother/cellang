package org.cellang.console.chart;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.cellang.commons.cache.Cache;

public class ChartModel<T> extends ChartSerial<T> {

	private Map<String, ChartSingleSerial<T>> serialMap = new HashMap<>();

	public ChartModel() {
		
	}

	public ChartModel(ChartSingleSerial<T> css) {
		this();
		this.addSerail(css);
	}
	public List<ChartSingleSerial<T>> getSerialList(){
		List<ChartSingleSerial<T>> rt = new ArrayList<>();
		rt.addAll(this.serialMap.values());
		return rt;
	}
	
	public ChartSingleSerial<T> getSerial(String sname) {
		return serialMap.get(sname);
	}

	public void addSerail(ChartSingleSerial<T> css) {
		String key = css.getName();
		ChartSingleSerial<T> old = this.serialMap.put(key, css);
		
	}

	@Override
	public int getWindowSize() {
		
		return this.serialMap.values().iterator().next().getWindowSize();
	}

	@Override
	public List<String> getSerialNameList() {
		List<String> rt = new ArrayList<>();
		rt.addAll(this.serialMap.keySet());
		return rt;
	}

	public String getXDisplayValue(T xValue) {
		return String.valueOf(xValue);//
	}

	@Override
	public T getXValue(int idx) {
		return this.serialMap.values().iterator().next().getXValue(idx);
	}

	@Override
	public BigDecimal getYValue(String name, T xValue) {
		return this.getSerial(name).getYValue(xValue);
	}
	
	@Override
	public void moveWindowTo(T startXValue) {
		for(ChartSerial<T> cs:this.serialMap.values()){
			cs.moveWindowTo(startXValue);//
		}		
	}

}
