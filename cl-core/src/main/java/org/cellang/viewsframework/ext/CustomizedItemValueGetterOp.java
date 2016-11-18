package org.cellang.viewsframework.ext;

import org.cellang.core.entity.CustomizedItemEntity;
import org.cellang.core.entity.CustomizedReportEntity;

public class CustomizedItemValueGetterOp extends AbstractReportItemValueGetterOp {

	CustomizedItemValueGetterOp(int agFun) {
		super(CustomizedReportEntity.tableName, CustomizedItemEntity.tableName, agFun);
	}

	public static CustomizedItemValueGetterOp newSumOp() {
		return new CustomizedItemValueGetterOp(AbstractReportItemValueGetterOp.AG_SUM);
	}

	@Override
	CustomizedItemValueGetterOp set(String corpId, int year, String key) {
		super.set(corpId, year, key);
		return this;
	}

}