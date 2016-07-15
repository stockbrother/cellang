package org.cellang.console.chart;

import java.math.BigDecimal;
import java.util.List;

import org.cellang.commons.cache.Cache;

public abstract class ChartSerial<T> {
	protected Cache<BigDecimal[]> actualMinMax;

	protected BigDecimal preferedMin = BigDecimal.ZERO;

	protected BigDecimal preferedMax = BigDecimal.ONE;

	protected long lastModified = -1;

	public abstract int getXCount();

	public abstract List<String> getSerialNameList();

	public abstract T getXValue(int idx);

	public abstract BigDecimal getYValue(String name, T xValue);

	public ChartSerial() {
		this.actualMinMax = new Cache<BigDecimal[]>(new Cache.Provider<BigDecimal[]>() {

			@Override
			public BigDecimal[] get() {

				return doGetActualMinMax();
			}

			@Override
			public long getModified() {
				return lastModified;
			}
		});
	}
	public abstract void clearPoints();
	
	public BigDecimal getPreferedMin() {
		return preferedMin;
	}

	public BigDecimal getPreferedMax() {
		return preferedMin;
	}

	public long getLastModified() {
		return lastModified;
	}

	public void setPreferedMin(BigDecimal preferedMin) {
		this.preferedMin = preferedMin;
	}

	public BigDecimal getDisplayMin() {
		BigDecimal actual = this.actualMinMax.get()[0];
		if (actual == null || actual.compareTo(this.preferedMin) > 0) {
			actual = this.preferedMin;
		}
		return actual;
	}

	public BigDecimal getDisplayMax() {
		BigDecimal actual = this.actualMinMax.get()[1];
		if (actual == null || actual.compareTo(this.preferedMax) < 0) {
			actual = this.preferedMin;
		}
		return actual;
	}

	protected BigDecimal[] doGetActualMinMax() {
		BigDecimal min = null;
		BigDecimal max = null;

		int count = this.getXCount();
		List<String> snameL = this.getSerialNameList();
		for (int i = 0; i < count; i++) {
			T xValue = this.getXValue(i);

			for (int j = 0; j < snameL.size(); j++) {
				String name = snameL.get(j);
				BigDecimal score = this.getYValue(name, xValue);
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
		}
		return new BigDecimal[] { min, max };
	}

	public void modified() {
		this.lastModified = System.currentTimeMillis();
	}

}
