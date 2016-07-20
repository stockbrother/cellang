package org.cellang.console.ext;

import org.cellang.core.entity.EntityObject;

public interface ExtendingPropertyDefine<T extends EntityObject> {

	public Class<T> getEntityClass();

	public String getKey();

	/**
	 * Initialize this property before using it.
	 * 
	 * @param context
	 * @return
	 */
	public boolean install(Object context);

	public Object getValue(EntityObject eo);

}
