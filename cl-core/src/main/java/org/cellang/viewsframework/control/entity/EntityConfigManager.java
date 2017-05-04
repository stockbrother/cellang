package org.cellang.viewsframework.control.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cellang.core.entity.CorpInfoEntity;
import org.cellang.core.entity.EntityConfig;
import org.cellang.core.entity.EntitySessionFactory;
import org.cellang.core.entity.ExtendingPropertyEntity;
import org.cellang.core.entity.FavoriteActionEntity;
import org.cellang.core.entity.InterestedCorpEntity;
import org.cellang.core.entity.QuotesEntity;
import org.cellang.viewsframework.View;
import org.cellang.viewsframework.control.EntityConfigSelectionListener;
import org.cellang.viewsframework.control.EntityConfigSelector;
import org.cellang.viewsframework.ops.OperationContext;
import org.cellang.viewsframework.table.EntityObjectTableView;

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
	OperationContext oc;
	public EntityConfigManager(OperationContext oc, EntitySessionFactory es) {
		this.oc = oc;
		this.entityService = es;
		controlMap.put(QuotesEntity.tableName, new QuotesEntityConfigControl(this.entityService));
		controlMap.put(CorpInfoEntity.tableName, new CorpInfoEntityConfigControl(oc, this.entityService));
		controlMap.put(ExtendingPropertyEntity.tableName, new ExtendingPropertyEntityConfigControl(this.entityService));
		controlMap.put(FavoriteActionEntity.tableName, new FavoriteActionEntityConfigControl(oc, this.entityService));
		controlMap.put(InterestedCorpEntity.tableName, new InterestedCorpEntityConfigControl(this.entityService));

	}

	public View newEntityListView(Class entityCls) {
		EntityConfig ec = this.entityService.getEntityConfigFactory().get(entityCls);
		if (ec == null) {
			throw new RuntimeException("no this entity class:" + entityCls);
		}
		return newEntityListView(ec);
	}

	public View newEntityListView(EntityConfig ec) {
		return newEntityListView(ec, new ArrayList<String>());
	}

	public View newEntityListView(EntityConfig ec, List<String> extPropL) {
		EntityConfigControl<?> ecc = this.getEntityConfigControl(ec);
		EntityObjectTableView rt = new EntityObjectTableView(oc, ec, ecc, extPropL, this.entityService,
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
