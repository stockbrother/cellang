package org.cellang.viewsframework.ext;

import org.cellang.core.entity.IncomeStatementItemEntity;
import org.cellang.core.entity.IncomeStatementReportEntity;

public class IncomeStatementItemValueGetterOp extends AbstractReportItemValueGetterOp {

	IncomeStatementItemValueGetterOp(int agFun) {
		super(IncomeStatementReportEntity.tableName, IncomeStatementItemEntity.tableName, agFun);
	}

	public static IncomeStatementItemValueGetterOp newSumOp() {
		return new IncomeStatementItemValueGetterOp(AbstractReportItemValueGetterOp.AG_SUM);
	}

	@Override
	IncomeStatementItemValueGetterOp set(String corpId, int year, String key) {
		super.set(corpId, year, key);
		return this;
	}

}