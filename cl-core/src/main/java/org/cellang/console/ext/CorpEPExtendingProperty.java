package org.cellang.console.ext;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

import org.cellang.console.view.ExtendingProperty;
import org.cellang.console.view.HasDelagates;
import org.cellang.core.entity.BalanceSheetItemEntity;
import org.cellang.core.entity.BalanceSheetReportEntity;
import org.cellang.core.entity.CorpInfoEntity;
import org.cellang.core.entity.EntityObject;
import org.cellang.core.entity.EntityOp;
import org.cellang.core.entity.EntitySession;
import org.cellang.core.entity.EntitySessionFactory;
import org.cellang.core.entity.IncomeStatementItemEntity;
import org.cellang.core.entity.IncomeStatementReportEntity;
import org.cellang.core.entity.QuotesEntity;

public class CorpEPExtendingProperty implements ExtendingProperty {
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

	private class JingLiRunGetterOp extends EntityOp<BigDecimal> {
		String corpId;
		int year;
		String sql = "select itm.value from " + IncomeStatementItemEntity.tableName + " itm,"
				+ IncomeStatementReportEntity.tableName
				+ " rpt where rpt.corpId=? and rpt.reportDate = ? and itm.reportId = rpt.id and itm.key=?";

		JingLiRunGetterOp set(String corpId, int year) {
			this.corpId = corpId;
			this.year = year;
			return this;
		}

		@Override
		public BigDecimal execute(EntitySession es) {
			Date date = new Date(this.year - 1900, 11, 31);
			List<Object[]> oL = es.getDataAccessTemplate().executeQuery(es.getConnection(), sql,
					new Object[] { corpId, date, "净利润" });

			BigDecimal bd = null;
			if (oL.size() == 1) {
				bd = (BigDecimal) oL.get(0)[0];
			} else {
				throw new RuntimeException("too many result.");
			}

			return bd;
		}
	};
	private class SszbGetterOp extends EntityOp<BigDecimal> {
		String corpId;
		int year;
		String sql = "select itm.value from " + BalanceSheetItemEntity.tableName + " itm,"
				+ BalanceSheetReportEntity.tableName
				+ " rpt where rpt.corpId=? and rpt.reportDate = ? and itm.reportId = rpt.id and itm.key=?";

		SszbGetterOp set(String corpId, int year) {
			this.corpId = corpId;
			this.year = year;
			return this;
		}

		@Override
		public BigDecimal execute(EntitySession es) {
			Date date = new Date(this.year - 1900, 11, 31);
			List<Object[]> oL = es.getDataAccessTemplate().executeQuery(es.getConnection(), sql,
					new Object[] { corpId, date, "实收资本" });

			BigDecimal bd = null;
			if (oL.size() == 1) {
				bd = (BigDecimal) oL.get(0)[0];
			} else {
				throw new RuntimeException("too many result.");
			}

			return bd;
		}
	};

	QuotesGetterOp quotesGetter = new QuotesGetterOp();

	JingLiRunGetterOp jlrGetter = new JingLiRunGetterOp();
	
	SszbGetterOp sszbGetter = new SszbGetterOp();

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
	public String getName() {

		return "ep";
	}

	@Override
	public Object getValue(EntityObject eo) {
		CorpInfoEntity ce = (CorpInfoEntity) eo;
		QuotesEntity qe = this.quotesGetter.set(eo.getId()).execute(this.esf);
		if (qe == null) {
			return null;
		}
		BigDecimal price = qe.getSettlement();
		BigDecimal sszb = this.sszbGetter.set(eo.getId(), 2015).execute(this.esf);
		if (sszb == null) {
			return null;
		}
		
		BigDecimal jlr = this.jlrGetter.set(eo.getId(), 2015).execute(this.esf);
		if (jlr == null) {
			return null;
		}
		BigDecimal price2 = price.multiply(sszb); 
		return price2.divide(jlr, 2, RoundingMode.HALF_UP);
	}

}
