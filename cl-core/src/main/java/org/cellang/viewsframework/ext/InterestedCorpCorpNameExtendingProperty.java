package org.cellang.viewsframework.ext;

import org.cellang.core.entity.InterestedCorpEntity;

public class InterestedCorpCorpNameExtendingProperty extends AbstractCorpNameExtendingProperty<InterestedCorpEntity> {

	public InterestedCorpCorpNameExtendingProperty() {
		super(InterestedCorpEntity.class);
	}

	@Override
	protected String getCorpId(InterestedCorpEntity eo) {
		return eo.getCorpId();
	}

}
