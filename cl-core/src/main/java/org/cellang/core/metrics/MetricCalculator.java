package org.cellang.core.metrics;

import java.util.Date;

public abstract class MetricCalculator {

	protected String key;
	ReportConfigFactory rcf;
	public MetricCalculator(ReportConfigFactory rcf,String key) {
		this.key = key;
		this.rcf = rcf;
	}

	public String getKey() {
		return key;
	}

	public abstract Double calculate(CorpMetricService ms, String corpId, Date date);

}
