package org.cellang.viewsframework.ext;

import org.cellang.core.entity.EntityOp;
import org.cellang.core.entity.EntitySession;
import org.cellang.core.entity.QuotesEntity;

class QuotesGetterOp extends EntityOp<QuotesEntity> {
	String corpId;

	QuotesGetterOp set(String corpId) {
		this.corpId = corpId;
		return this;
	}

	@Override
	public QuotesEntity execute(EntitySession es) {

		return es.getSingle(QuotesEntity.class, "code", corpId);

	}
}