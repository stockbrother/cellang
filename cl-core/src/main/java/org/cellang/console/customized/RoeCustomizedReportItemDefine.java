package org.cellang.console.customized;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.cellang.console.ext.BalanceSheetItemValueGetterOp;
import org.cellang.console.ext.IncomeStatementItemValueGetterOp;

public class RoeCustomizedReportItemDefine extends AbstractCustomizedReportItemDefine {
	IncomeStatementItemValueGetterOp jlrGetter = IncomeStatementItemValueGetterOp.newSumOp();

	BalanceSheetItemValueGetterOp sszbGetter = BalanceSheetItemValueGetterOp.newSumOp();

	RoeCustomizedReportItemDefine() {
		super("ROE");
	}

	@Override
	public BigDecimal getValue(String corpId, int year) {
		BigDecimal syzqy = this.sszbGetter.set(corpId, year, year, "所有者权益合计")
				.execute(this.esf);
		if (syzqy == null) {
			return null;
		}

		BigDecimal jlr = this.jlrGetter.set(corpId, year, year, "净利润").execute(this.esf);
		if (jlr == null) {
			return null;
		}

		return jlr.divide(syzqy, 2, RoundingMode.HALF_UP);
	}

}
