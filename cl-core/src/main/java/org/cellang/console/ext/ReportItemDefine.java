package org.cellang.console.ext;

import java.math.BigDecimal;

import org.cellang.core.entity.EntitySessionFactory;

public class ReportItemDefine {

	ReportType type;
	String key;
	AbstractReportItemValueGetterOp getter;

	private ReportItemDefine(ReportType type, String key) {
		this.type = type;
		this.key = key;
		switch (this.type) {
		case BALANCE_SHEET:
			getter = BalanceSheetItemValueGetterOp.newSumOp();
			break;
		case INCOME_STATEMENT:
			getter = IncomeStatementItemValueGetterOp.newSumOp();
			break;
		case CASH_FLOW:
		default:
			throw new RuntimeException("TODO");
		}
	}

	public static ReportItemDefine valueOf(ReportType type, String key) {
		return new ReportItemDefine(type, key);
	}

	public BigDecimal getValue(String corpId, int year, EntitySessionFactory esf) {
		getter.set(corpId, year, this.key);
		return getter.execute(esf);
	}
}
