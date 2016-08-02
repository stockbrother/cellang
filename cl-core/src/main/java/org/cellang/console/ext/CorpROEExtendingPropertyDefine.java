package org.cellang.console.ext;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.cellang.core.entity.CorpInfoEntity;
import org.cellang.core.entity.EntityObject;

public class CorpROEExtendingPropertyDefine extends AbstractCorpExtendingPropertyDefine {
	IncomeStatementItemValueGetterOp jlrGetter = IncomeStatementItemValueGetterOp.newSumOp();

	BalanceSheetItemValueGetterOp sszbGetter = BalanceSheetItemValueGetterOp.newSumOp();

	int years = 1;

	public CorpROEExtendingPropertyDefine(int years) {
		super("ROE(" + years + ")");
		this.years = years;
	}

	@Override
	public Object calculate(EntityObject eo) {
		CorpInfoEntity ce = (CorpInfoEntity) eo;

		int thisYear = 2015;

		BigDecimal syzqy = this.sszbGetter.set(eo.getId(), thisYear, thisYear - this.years, "所有者权益合计")
				.execute(this.esf);
		if (syzqy == null) {
			return null;
		}

		BigDecimal jlr = this.jlrGetter.set(eo.getId(), thisYear, thisYear - this.years, "净利润").execute(this.esf);
		if (jlr == null) {
			return null;
		}

		return jlr.divide(syzqy, 2, RoundingMode.HALF_UP);
	}

}
