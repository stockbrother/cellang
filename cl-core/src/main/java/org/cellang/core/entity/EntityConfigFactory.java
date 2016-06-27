package org.cellang.core.entity;

import java.util.HashMap;
import java.util.Map;

public class EntityConfigFactory {

	private Map<Class, EntityConfig> entityConfigMap = new HashMap<Class, EntityConfig>();

	public void addEntity(EntityConfig ec) {
		this.entityConfigMap.put(ec.getEntityClass(), ec);
	}

	public EntityConfig get(Class entityClass) {
		return this.entityConfigMap.get(entityClass);
	}
}
