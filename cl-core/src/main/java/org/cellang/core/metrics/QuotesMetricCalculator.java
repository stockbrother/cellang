package org.cellang.core.metrics;

import java.util.Date;

import org.cellang.core.entity.QuotesEntity;

public class QuotesMetricCalculator extends MetricCalculator {

	public QuotesMetricCalculator(ReportConfigFactory rcf) {
		super(rcf, "QUOTES");

	}

	@Override
	public Double calculate(CorpMetricService ms, String corpId, Date date) {
		QuotesEntity qe = ms.getEntityService().getSingle(QuotesEntity.class, "code", corpId);
		if (qe == null) {
			return null;
		}
		return qe.getSettlement().doubleValue();
	}

}
