package org.cellang.viewsframework.customized;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.cellang.viewsframework.ext.BalanceSheetItemValueGetterOp;
import org.cellang.viewsframework.ext.IncomeStatementItemValueGetterOp;

/**
 * @see CustomizedReportUpdater
 * @author wu
 *
 */
public class YingshouTurnDaysCustomizedReportItemDefine extends AbstractCustomizedReportItemDefine {
	public static final BigDecimal b360 = new BigDecimal(360);

	public static final String KEY = "YS-TURN-DAYS";

	IncomeStatementItemValueGetterOp isGetter = IncomeStatementItemValueGetterOp.newSumOp();

	BalanceSheetItemValueGetterOp bsGetter = BalanceSheetItemValueGetterOp.newSumOp();

	YingshouTurnDaysCustomizedReportItemDefine() {
		super(KEY);
	}

	@Override
	public BigDecimal getValue(String corpId, int year) {
		BigDecimal chengben = this.isGetter.set(corpId, year, year, "营业收入").execute(this.esf);
		if (chengben == null) {
			return null;
		}

		BigDecimal ch = this.bsGetter.set(corpId, year, year, "应收账款").execute(this.esf);
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
