package org.cellang.core.entity;

public abstract class EntityOp<T> {

	public T execute(EntitySessionFactory entityService) {
		return entityService.execute(this);
	}

	public abstract T execute(EntitySession es);

}
