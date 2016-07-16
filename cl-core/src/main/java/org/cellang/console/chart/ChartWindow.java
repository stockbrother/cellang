package org.cellang.console.chart;

import java.math.BigDecimal;

public abstract class ChartWindow<T> {

	protected BigDecimal preferedMin = BigDecimal.ZERO;

	protected BigDecimal preferedMax = BigDecimal.ONE;

	public ChartWindow() {

	}

	public abstract int getWindowSize();

	public abstract void moveWindowTo(T startXValue);

	public abstract T getXValue(int idx);

	protected abstract BigDecimal[] doGetActualMinMax();
	
	public BigDecimal getPreferedMin() {
		return preferedMin;
	}

	public BigDecimal getPreferedMax() {
		return preferedMin;
	}

	public void setPreferedMin(BigDecimal preferedMin) {
		this.preferedMin = preferedMin;
	}

	public BigDecimal getDisplayYMin() {
		BigDecimal actual = this.doGetActualMinMax()[0];
		if (actual == null || actual.compareTo(this.preferedMin) > 0) {
			actual = this.preferedMin;
		}
		return actual;
	}

	public BigDecimal getDisplayYMax() {
		BigDecimal actual = this.doGetActualMinMax()[1];
		if (actual == null || actual.compareTo(this.preferedMax) < 0) {
			actual = this.preferedMin;
		}
		return actual;
	}

}
