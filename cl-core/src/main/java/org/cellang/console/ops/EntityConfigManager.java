package org.cellang.console.ops;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cellang.console.control.CorpInfoEntityConfigControl;
import org.cellang.console.control.EntityConfigControl;
import org.cellang.console.control.EntityConfigSelectionListener;
import org.cellang.console.control.EntityConfigSelector;
import org.cellang.console.control.QuotesEntityConfigControl;
import org.cellang.console.view.EntityObjectTableView;
import org.cellang.console.view.View;
import org.cellang.core.entity.CorpInfoEntity;
import org.cellang.core.entity.EntityConfig;
import org.cellang.core.entity.EntitySessionFactory;
import org.cellang.core.entity.QuotesEntity;

public class EntityConfigManager implements EntityConfigSelector {

	private int queryLimit = 1000;

	private EntitySessionFactory entityService;

	private List<EntityConfigSelectionListener> selectionListenerList = new ArrayList<>();

	private Map<String, EntityConfigControl<?>> controlMap = new HashMap<>();

	public EntityConfigManager(EntitySessionFactory es) {
		this.entityService = es;
		controlMap.put(QuotesEntity.tableName, new QuotesEntityConfigControl(this.entityService));
		controlMap.put(CorpInfoEntity.tableName, new CorpInfoEntityConfigControl(this.entityService));
		
	}

	public View newEntityListView(EntityConfig ec) {
		EntityConfigControl<?> ecc = this.getEntityConfigControl(ec);
		EntityObjectTableView rt = new EntityObjectTableView(ec, ecc, this.entityService, this.queryLimit);

		return rt;
	}

	public EntityConfigControl<?> getEntityConfigControl(Class<?> entityClass) {
		EntityConfig ec = this.entityService.getEntityConfigFactory().get(entityClass);
		return this.getEntityConfigControl(ec);
	}

	public EntityConfigControl<?> getEntityConfigControl(EntityConfig ec) {
		EntityConfigControl<?> rt = this.controlMap.get(ec.getTableName());
		return rt;
	}

	@Override
	public void addEntityConfigSelectionListener(EntityConfigSelectionListener esl) {
		this.selectionListenerList.add(esl);
	}

}
