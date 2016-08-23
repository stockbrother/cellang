package org.cellang.console.customized;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.cellang.console.ext.BalanceSheetItemValueGetterOp;
import org.cellang.console.ext.IncomeStatementItemValueGetterOp;

/**
 * @see CustomizedReportUpdater
 * @author wu
 *
 */
public class YingfuTurnDaysCustomizedReportItemDefine extends AbstractCustomizedReportItemDefine {
	public static final BigDecimal b360 = new BigDecimal(360);

	public static final String KEY = "YF-TURN-DAYS";

	IncomeStatementItemValueGetterOp isGetter = IncomeStatementItemValueGetterOp.newSumOp();

	BalanceSheetItemValueGetterOp bsGetter = BalanceSheetItemValueGetterOp.newSumOp();

	YingfuTurnDaysCustomizedReportItemDefine() {
		super(KEY);
	}

	@Override
	public BigDecimal getValue(String corpId, int year) {
		BigDecimal chengben = this.isGetter.set(corpId, year, year, "营业成本").execute(this.esf);
		if (chengben == null) {
			return null;
		}

		BigDecimal ch = this.bsGetter.set(corpId, year, year, "应付账款").execute(this.esf);
		if (ch == null) {
			return null;
		}
		if (ch.compareTo(BigDecimal.ZERO) == 0) {
			return null;
		}

		BigDecimal lv = chengben.divide(ch, 2, RoundingMode.HALF_UP);
		if (lv.compareTo(BigDecimal.ZERO) == 0) {
			return null;
		}

		return b360.divide(lv, 2, RoundingMode.HALF_UP);
	}

}