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
import org.cellang.commons.cache.Provider;

public class ChartModel<T> extends ChartSerial<T> {

	private Map<String, ChartSingleSerial<T>> serialMap = new HashMap<>();

	private Cache<List<T>> xList;

	private Comparator<T> xValueSorter;

	public Comparator<T> getxValueSorter() {
		return xValueSorter;
	}

	public void setxValueSorter(Comparator<T> xValueSorter) {
		this.xValueSorter = xValueSorter;
	}

	public ChartModel() {

		this.xList = new Cache<List<T>>(new Provider<List<T>>() {

			@Override
			public List<T> get() {

				return doGetXlist();
			}

			@Override
			public long getModified() {
				return doGetModified();
			}
		});
	}

	public ChartModel(ChartSingleSerial<T> css) {
		this();
		this.addSerail(css);
	}

	public ChartSingleSerial<T> getSerial(String sname) {
		return serialMap.get(sname);
	}

	public void addSerail(ChartSingleSerial<T> css) {
		String key = css.getName();
		ChartSingleSerial<T> old = this.serialMap.put(key, css);
		this.modified();
	}

	protected long doGetModified() {
		long modified = -1;
		for (ChartSingleSerial<T> cs : serialMap.values()) {
			if (modified < cs.lastModified) {
				modified = cs.lastModified;
			}
		}
		return modified;
	}

	protected List<T> doGetXlist() {

		Set<T> set = new HashSet<>();

		for (ChartSingleSerial<T> css : serialMap.values()) {
			int countI = css.getXCount();
			for (int i = 0; i < countI; i++) {
				set.add(css.getXValue(i));
			}
		}
		Object[] xA = new Object[set.size()];
		xA = set.toArray(xA);
		if (this.xValueSorter == null) {
			Arrays.sort(xA);//
		} else {
			//Arrays.sort(xA, this.xValueSorter);
		}

		List<T> rt = new ArrayList<T>();
		for (Object x : xA) {
			rt.add((T) x);
		}
		return rt;
	}

	@Override
	public int getXCount() {
		return this.xList.get().size();
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
		return this.xList.get().get(idx);//
	}

	@Override
	public BigDecimal getYValue(String name, T xValue) {
		return this.getSerial(name).getYValue(xValue);
	}

}
