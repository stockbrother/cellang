package org.cellang.core.entity;

/**
 * Entity operation, which is designed to be executed with a EntitySession feed
 * by EntitySessionFactory.
 * 
 * @see EntitySessionFactory 
 * @see EntitySession
 * @author wu
 *
 * @param <T>
 */
public abstract class EntityOp<T> {

	public T execute(EntitySessionFactory entityService) {
		return entityService.execute(this);
	}

	public abstract T execute(EntitySession es);

}
