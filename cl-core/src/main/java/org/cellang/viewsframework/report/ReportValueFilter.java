package org.cellang.viewsframework.report;

import java.math.BigDecimal;

public interface ReportValueFilter {

	public BigDecimal getValue(int year, BigDecimal value, ReportRow rr);
}
