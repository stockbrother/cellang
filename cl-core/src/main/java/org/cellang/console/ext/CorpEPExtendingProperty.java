package org.cellang.console.ext;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.cellang.console.view.HasDelagates;
import org.cellang.core.entity.CorpInfoEntity;
import org.cellang.core.entity.EntityObject;
import org.cellang.core.entity.EntityOp;
import org.cellang.core.entity.EntitySession;
import org.cellang.core.entity.EntitySessionFactory;
import org.cellang.core.entity.QuotesEntity;

public class CorpEPExtendingProperty implements SavableExtendingPropertyDefine<CorpInfoEntity> {
	EntitySessionFactory esf;

	private class QuotesGetterOp extends EntityOp<QuotesEntity> {
		String corpId;

		QuotesGetterOp set(String corpId) {
			this.corpId = corpId;
			return this;
		}

		@Override
		public QuotesEntity execute(EntitySession es) {

			return es.getSingle(QuotesEntity.class, "code", corpId);

		}
	};

	QuotesGetterOp quotesGetter = new QuotesGetterOp();

	IncomeStatementItemValueGetterOp jlrGetter = IncomeStatementItemValueGetterOp.newSumOp();

	BalanceSheetItemValueGetterOp sszbGetter = BalanceSheetItemValueGetterOp.newSumOp();

	int years;

	public CorpEPExtendingProperty(int years) {
		this.years = years;
	}

	@Override
	public boolean install(Object context) {
		if (context instanceof EntitySessionFactory) {
			this.esf = (EntitySessionFactory) context;
			return true;
		}
		if (context instanceof HasDelagates) {
			HasDelagates dela = (HasDelagates) context;
			EntitySessionFactory esf = dela.getDelegate(EntitySessionFactory.class);
			if (esf == null) {
				return false;
			}
			this.esf = esf;
			return true;
		}
		return false;
	}

	@Override
	public String getKey() {
		return "P/E(" + years + ")";
	}

	@Override
	public Object getValue(EntityObject eo) {
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

	@Override
	public Class<CorpInfoEntity> getEntityClass() {
		return CorpInfoEntity.class;
	}

}
