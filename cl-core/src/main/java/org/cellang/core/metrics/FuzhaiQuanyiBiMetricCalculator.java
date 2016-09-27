package org.cellang.core.metrics;

import java.util.Date;

public class FuzhaiQuanyiBiMetricCalculator extends MetricCalculator {

	public FuzhaiQuanyiBiMetricCalculator(ReportConfigFactory rcf) {
		super(rcf, "负债权益比");

	}

	@Override
	public Double calculate(CorpMetricService ms, String corpId, Date date) {
		// youxifuzhai
		Double fuzhai = null;//TODO ms.getReportItem(rcf.balanceSheetReportConfig, corpId, date, "负债合计");
		Double quanyi = null;//TODO ms.getReportItem(rcf.balanceSheetReportConfig, corpId, date, "所有者权益合计");
		if (quanyi == null) {
			return null;
		}
		double d1 = fuzhai == null ? 0 : fuzhai.doubleValue();
		double d2 = quanyi == null ? 0 : quanyi.doubleValue();
		return d1 / d2;
	}

}
