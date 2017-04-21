package org.cellang.corpsviewer;

import java.math.BigDecimal;

import org.cellang.viewsframework.view.report.ReportRow;
import org.cellang.viewsframework.view.report.ReportValueFilter;

public class BalanceSheetReportValueFilter implements ReportValueFilter{

	@Override
	public BigDecimal getValue(int year, BigDecimal value, ReportRow rr){
		BigDecimal rt = value;
		
		return rt;
	}
}
