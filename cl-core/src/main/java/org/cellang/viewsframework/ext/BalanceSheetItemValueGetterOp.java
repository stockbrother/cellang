package org.cellang.viewsframework.ext;

import org.cellang.core.entity.BalanceSheetItemEntity;
import org.cellang.core.entity.BalanceSheetReportEntity;

public class BalanceSheetItemValueGetterOp extends AbstractReportItemValueGetterOp {

	BalanceSheetItemValueGetterOp(int agFun) {
		super(BalanceSheetReportEntity.tableName, BalanceSheetItemEntity.tableName, agFun);
	}

	public static BalanceSheetItemValueGetterOp newSumOp() {
		return new BalanceSheetItemValueGetterOp(AbstractReportItemValueGetterOp.AG_SUM);
	}

	@Override
	BalanceSheetItemValueGetterOp set(String corpId, int year, String key) {
		super.set(corpId, year, key);
		return this;
	}

}