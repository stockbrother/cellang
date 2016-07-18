package org.cellang.console.ops;

import org.cellang.console.view.EntityObjectTableView;
import org.cellang.console.view.View;
import org.cellang.core.entity.EntityConfig;
import org.cellang.core.entity.EntitySessionFactory;

public class EntityConfigManager {

	private int queryLimit = 1000;

	private EntitySessionFactory entityService;

	public EntityConfigManager(EntitySessionFactory es) {
		this.entityService = es;
	}

	public View newEntityListView(EntityConfig ec) {
		
		EntityObjectTableView rt = new EntityObjectTableView(ec, this.entityService, this.queryLimit);

		return rt;
	}

}
