package org.cellang.console.chart;

import java.math.BigDecimal;
import java.util.List;

import org.cellang.commons.cache.Cache;

public abstract class ChartSerial<T> {

	protected BigDecimal preferedMin = BigDecimal.ZERO;

	protected BigDecimal preferedMax = BigDecimal.ONE;
	
	public ChartSerial() {
		
	}
		
	
	public abstract int getWindowSize();
	
	public abstract void moveWindowTo(T startXValue);

	public abstract List<String> getSerialNameList();

	public abstract T getXValue(int idx);

	public abstract BigDecimal getYValue(String name, T xValue);

	public BigDecimal getPreferedMin() {
		return preferedMin;
	}

	public BigDecimal getPreferedMax() {
		return preferedMin;
	}

	public void setPreferedMin(BigDecimal preferedMin) {
		this.preferedMin = preferedMin;
	}

	public BigDecimal getDisplayMin() {
		BigDecimal actual = this.doGetActualMinMax()[0];
		if (actual == null || actual.compareTo(this.preferedMin) > 0) {
			actual = this.preferedMin;
		}
		return actual;
	}

	public BigDecimal getDisplayMax() {
		BigDecimal actual = this.doGetActualMinMax()[1];
		if (actual == null || actual.compareTo(this.preferedMax) < 0) {
			actual = this.preferedMin;
		}
		return actual;
	}

	protected BigDecimal[] doGetActualMinMax() {
		BigDecimal min = null;
		BigDecimal max = null;

		int count = this.getWindowSize();
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

}
