package org.cellang.viewsframework.ext;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.cellang.core.entity.CorpInfoEntity;
import org.cellang.core.entity.EntityObject;
import org.cellang.core.entity.EntitySessionFactory;
import org.cellang.core.entity.QuotesEntity;
import org.cellang.viewsframework.HasDelegates;

public class CorpP_EBITDAExtendingProperty  extends AbstractExtendingPropertyDefine<CorpInfoEntity, BigDecimal> {
	EntitySessionFactory esf;
	
	QuotesGetterOp quotesGetter = new QuotesGetterOp();

	IncomeStatementItemValueGetterOp isiGetter = IncomeStatementItemValueGetterOp.newSumOp();

	BalanceSheetItemValueGetterOp bsiGetter = BalanceSheetItemValueGetterOp.newSumOp();

	int years;

	public CorpP_EBITDAExtendingProperty(int years) {
		super(CorpInfoEntity.class, BigDecimal.class);
		this.years = years;
	}

	@Override
	public boolean install(Object context) {
		if (context instanceof EntitySessionFactory) {
			this.esf = (EntitySessionFactory) context;
			return true;
		}
		if (context instanceof HasDelegates) {
			HasDelegates dela = (HasDelegates) context;
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
		return "P/EBITDA(" + years + ")";
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
		BigDecimal sszb = this.bsiGetter.set(eo.getId(), thisYear, thisYear - this.years, "实收资本").execute(this.esf);
		if (sszb == null) {
			return null;
		}

		BigDecimal yysr = this.isiGetter.set(eo.getId(), thisYear, thisYear - this.years, "营业收入").execute(this.esf);
		if (yysr == null) {
			return null;
		}
		BigDecimal yycb = this.isiGetter.set(eo.getId(), thisYear, thisYear - this.years, "营业成本").execute(this.esf);
		if (yycb == null) {
			return null;
		}
		BigDecimal xsfy = this.isiGetter.set(eo.getId(), thisYear, thisYear - this.years, "销售费用").execute(this.esf);
		if (xsfy == null) {
			return null;
		}
		BigDecimal glfy = this.isiGetter.set(eo.getId(), thisYear, thisYear - this.years, "管理费用").execute(this.esf);
		if (glfy == null) {
			return null;
		}
		
		BigDecimal ebitda = yysr.subtract(yycb).subtract(xsfy).subtract(glfy);
		if (BigDecimal.ZERO.equals(ebitda)) {
			//
			return BigDecimal.ZERO;
		}
		
		BigDecimal price2 = price.multiply(sszb);
		return price2.divide(ebitda, 2, RoundingMode.HALF_UP);
	}

}
