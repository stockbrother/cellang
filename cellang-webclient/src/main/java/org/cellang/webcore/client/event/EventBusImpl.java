/**
 * All right is from Author of the file,to be explained in comming days.
 * Sep 22, 2012
 */
package org.cellang.webcore.client.event;

import org.cellang.webcore.client.Container;
import org.cellang.webcore.client.lang.AbstractWebObject;

/**
 * @author wu
 * 
 */
public class EventBusImpl extends AbstractWebObject implements EventBus {

	/**
	 * @param c
	 */
	public EventBusImpl(Container c) {
		super(c);
	}

	@Override
	public EventBus getEventBus(boolean force) {
		return this;
	}


}
