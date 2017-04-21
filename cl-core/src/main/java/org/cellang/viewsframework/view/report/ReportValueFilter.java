package org.cellang.viewsframework.view.report;

import java.math.BigDecimal;

public interface ReportValueFilter {

	public BigDecimal getValue(int year, BigDecimal value, ReportRow rr);
}
