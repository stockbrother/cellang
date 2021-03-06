package org.cellang.viewsframework.ext;

import org.cellang.core.entity.EntityObject;

public abstract class AbstractExtendingPropertyDefine<E extends EntityObject, T>
		implements ExtendingPropertyDefine<E, T> {
	Class<E> entityClass;
	Class<T> valueClass;
	boolean savable = true;

	public AbstractExtendingPropertyDefine(Class<E> ec, Class<T> tc) {
		this.entityClass = ec;
		this.valueClass = tc;
	}

	@Override
	public boolean isSavable() {
		return this.savable;
	}

	@Override
	public Class<E> getEntityClass() {
		return this.entityClass;
	}

	@Override
	public Class<T> getValueClass() {
		return this.valueClass;
	}

}
