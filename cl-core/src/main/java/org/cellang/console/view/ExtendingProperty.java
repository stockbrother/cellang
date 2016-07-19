package org.cellang.console.view;

import org.cellang.core.entity.EntityObject;

public interface ExtendingProperty {

	public String getName();

	/**
	 * Initialize this property before using it.
	 * 
	 * @param context
	 * @return
	 */
	public boolean install(Object context);

	public Object getValue(EntityObject eo);

}
