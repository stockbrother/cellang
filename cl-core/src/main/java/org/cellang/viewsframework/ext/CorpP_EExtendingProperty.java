package org.cellang.viewsframework.ext;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.cellang.core.entity.CorpInfoEntity;
import org.cellang.core.entity.EntityObject;
import org.cellang.core.entity.QuotesEntity;

public class CorpP_EExtendingProperty extends AbstractCorpExtendingPropertyDefine {

	QuotesGetterOp quotesGetter = new QuotesGetterOp();

	IncomeStatementItemValueGetterOp jlrGetter = IncomeStatementItemValueGetterOp.newSumOp();

	BalanceSheetItemValueGetterOp sszbGetter = BalanceSheetItemValueGetterOp.newSumOp();

	int years;

	public CorpP_EExtendingProperty(int years) {
		super("P/E(" + years + ")");
		this.years = years;
	}

	@Override
	public Object calculate(EntityObject eo) {
		CorpInfoEntity ce = (CorpInfoEntity) eo;
		QuotesEntity qe = this.quotesGetter.set(eo.getId()).execute(this.esf);
		if (qe == null) {
			return null;
		}
		int thisYear = 2015;
		BigDecimal price = qe.getSettlement();
		BigDecimal sszb = this.sszbGetter.set(eo.getId(), thisYear, thisYear - this.years, "实收资本").execute(this.esf);
		if (sszb == null) {
			return null;
		}

		BigDecimal jlr = this.jlrGetter.set(eo.getId(), thisYear, thisYear - this.years, "净利润").execute(this.esf);
		if (jlr == null) {
			return null;
		}
		BigDecimal price2 = price.multiply(sszb);
		if (BigDecimal.ZERO.equals(jlr)) {
			return BigDecimal.ZERO;
		}
		return price2.divide(jlr, 2, RoundingMode.HALF_UP);
	}

}
