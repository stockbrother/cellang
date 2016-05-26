/**
 * All right is from Author of the file,to be explained in comming days.
 * Sep 30, 2012
 */
package org.cellang.clwt.core.client.event;

import org.cellang.clwt.core.client.lang.WebObject;

/**
 * @author wu
 * 
 */
public class LocalEvent extends Event {

	public static final Event.Type<LocalEvent> TYPE = new Event.Type<LocalEvent>("local");

	/**
	 * @param type
	 */
	public LocalEvent(Type<? extends Event> type, WebObject src) {
		super(type, src);
		this.isGlobal = false;
	}

}
