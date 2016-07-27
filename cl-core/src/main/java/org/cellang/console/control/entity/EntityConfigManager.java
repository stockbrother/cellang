package org.cellang.console.control.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cellang.console.control.EntityConfigSelectionListener;
import org.cellang.console.control.EntityConfigSelector;
import org.cellang.console.ops.OperationContext;
import org.cellang.console.view.View;
import org.cellang.console.view.helper.HelpersPane;
import org.cellang.console.view.table.EntityObjectTableView;
import org.cellang.core.entity.CorpInfoEntity;
import org.cellang.core.entity.EntityConfig;
import org.cellang.core.entity.EntitySessionFactory;
import org.cellang.core.entity.ExtendingPropertyEntity;
import org.cellang.core.entity.FavoriteActionEntity;
import org.cellang.core.entity.InterestedCorpEntity;
import org.cellang.core.entity.QuotesEntity;

/**
 * 
 * @author wu
 *
 */
public class EntityConfigManager implements EntityConfigSelector {

	private int queryLimit = 1000;

	private EntitySessionFactory entityService;

	private List<EntityConfigSelectionListener> selectionListenerList = new ArrayList<>();

	private Map<String, EntityConfigControl<?>> controlMap = new HashMap<>();

	HelpersPane helpers;

	public EntityConfigManager(OperationContext oc, EntitySessionFactory es, HelpersPane helpers) {
		this.entityService = es;
		this.helpers = helpers;
		this.helpers.entityHelper.setEntityConfigManager(this);// NOTE:
		controlMap.put(QuotesEntity.tableName, new QuotesEntityConfigControl(this.entityService));
		controlMap.put(CorpInfoEntity.tableName, new CorpInfoEntityConfigControl(oc, this.entityService));
		controlMap.put(ExtendingPropertyEntity.tableName, new ExtendingPropertyEntityConfigControl(this.entityService));
		controlMap.put(FavoriteActionEntity.tableName, new FavoriteActionEntityConfigControl(oc, this.entityService));
		controlMap.put(InterestedCorpEntity.tableName, new InterestedCorpEntityConfigControl(this.entityService));

	}

	public View newEntityListView(EntityConfig ec) {
		return newEntityListView(ec, new ArrayList<String>());
	}

	public View newEntityListView(EntityConfig ec, List<String> extPropL) {
		EntityConfigControl<?> ecc = this.getEntityConfigControl(ec);
		EntityObjectTableView rt = new EntityObjectTableView(helpers, ec, ecc, extPropL, this.entityService,
				this.queryLimit);

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
