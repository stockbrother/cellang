package org.cellang.core;

import java.util.Date;

public abstract class MetricCalculator {

	protected String key;

	public MetricCalculator(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

	public abstract Double calculate(CorpMetricService ms, String corpId, Date date);

}
