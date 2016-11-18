package org.cellang.viewsframework.customized;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.cellang.viewsframework.ext.BalanceSheetItemValueGetterOp;
import org.cellang.viewsframework.ext.CustomizedItemValueGetterOp;
import org.cellang.viewsframework.ext.IncomeStatementItemValueGetterOp;

/**
 * @see CustomizedReportUpdater
 * @author wu
 *
 */
public class CashTurnDaysCustomizedReportItemDefine extends AbstractCustomizedReportItemDefine {
	CustomizedItemValueGetterOp cuGetter = CustomizedItemValueGetterOp.newSumOp();
	public static final BigDecimal b360 = new BigDecimal(360);
	public static final String KEY = "CASH-TURN-DAYS";

	CashTurnDaysCustomizedReportItemDefine() {
		super(KEY);
	}

	@Override
	public BigDecimal getValue(String corpId, int year) {
		BigDecimal yf = this.getDays(corpId, year, YingfuTurnDaysCustomizedReportItemDefine.KEY);
		if (yf == null) {
			return null;
		}

		BigDecimal ch = this.getDays(corpId, year, InventoryTurnDaysCustomizedReportItemDefine.KEY);
		if (ch == null) {
			return null;
		}

		BigDecimal ys = this.getDays(corpId, year, YingshouTurnDaysCustomizedReportItemDefine.KEY);
		if (ys == null) {
			return null;
		}

		BigDecimal rt = ch.add(ys).subtract(yf);
		return rt;
	}

	private BigDecimal getDays(String corpId, int year, String item) {
		BigDecimal yf = this.cuGetter.set(corpId, year, year, item).execute(this.esf);
		return yf;
	}

}
