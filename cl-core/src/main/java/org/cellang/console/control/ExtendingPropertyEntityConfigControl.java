package org.cellang.console.control;

import org.cellang.console.ext.CorpNameExtExtProperty;
import org.cellang.core.entity.CorpInfoEntity;
import org.cellang.core.entity.EntitySessionFactory;

public class ExtendingPropertyEntityConfigControl extends EntityConfigControl<CorpInfoEntity> {

	public ExtendingPropertyEntityConfigControl(EntitySessionFactory entityService) {
		this.addExtendingProperty(new CorpNameExtExtProperty(), true);
	}

}
