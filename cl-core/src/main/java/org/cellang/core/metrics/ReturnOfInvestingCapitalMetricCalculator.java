package org.cellang.core.metrics;

import java.util.Date;

public class ReturnOfInvestingCapitalMetricCalculator extends MetricCalculator {
	public ReturnOfInvestingCapitalMetricCalculator(ReportConfigFactory rcf) {
		super(rcf, "EPS");
	}

	@Override
	public Double calculate(CorpMetricService ms, String corpId, Date date) {
		// youxifuzhai
		Double jlr = ms.getReportItem(rcf.incomeStatementReportConfig, corpId, date, "净利润");
		Double sszb = ms.getReportItem(rcf.balanceSheetReportConfig, corpId, date, "实收资本");
		if (jlr == null || sszb == null) {
			return null;
		}
		double d1 = jlr.doubleValue();
		double d2 = sszb.doubleValue();
		return d1 / d2;
	}

}
