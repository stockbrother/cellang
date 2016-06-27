package org.cellang.core;

import java.util.Date;

public class FuzhaiQuanyiBiMetricCalculator extends MetricCalculator {

	public FuzhaiQuanyiBiMetricCalculator() {
		super("负债权益比");
	}

	@Override
	public Double calculate(CorpMetricService ms, String corpId, Date date) {
		// youxifuzhai
		Double fuzhai = ms.getBlanceSheetItem(corpId, date, "负债合计");
		Double quanyi = ms.getBlanceSheetItem(corpId, date, "所有者权益合计");
		return fuzhai.doubleValue() / quanyi.doubleValue();
	}

}
