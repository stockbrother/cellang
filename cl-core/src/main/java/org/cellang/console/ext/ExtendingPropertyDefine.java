package org.cellang.console.ext;

import org.cellang.core.entity.EntityObject;

public interface ExtendingPropertyDefine<E extends EntityObject,T> {

	public Class<E> getEntityClass();

	public String getKey();

	/**
	 * Initialize this property before using it.
	 * 
	 * @param context
	 * @return
	 */
	public boolean install(Object context);

	public Object getValue(EntityObject eo);
	
	public Class<T> getValueClass();

}
