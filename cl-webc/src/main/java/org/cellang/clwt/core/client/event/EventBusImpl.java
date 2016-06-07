/**
 * All right is from Author of the file,to be explained in comming days.
 * Sep 22, 2012
 */
package org.cellang.clwt.core.client.event;

import org.cellang.clwt.core.client.Container;
import org.cellang.clwt.core.client.lang.AbstractWebObject;

/**
 * @author wu
 * 
 */
public class EventBusImpl extends AbstractWebObject implements EventBus {

	/**
	 * @param c
	 */
	public EventBusImpl(Container c) {
		super(c,"eventBus");
	}

	@Override
	public EventBus getEventBus(boolean force) {
		return this;
	}


}
