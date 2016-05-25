/**
 * All right is from Author of the file,to be explained in comming days.
 * Sep 22, 2012
 */
package org.cellang.webcore.client.event;

import org.cellang.webcore.client.lang.WebObject;

/**
 * @author wu
 * 
 */
public class StateChangeEvent extends Event {

	public static final Event.Type<StateChangeEvent> TYPE = new Event.Type<StateChangeEvent>("state-change");

	/**
	 * @param type
	 */
	public StateChangeEvent(WebObject src) {
		this(TYPE, src);
	}

	public StateChangeEvent(Type<? extends StateChangeEvent> type, WebObject src) {
		super(type, src);
	}

	public static StateChangeEvent dispatch(WebObject src) {
		StateChangeEvent evt = new StateChangeEvent(src);
		return evt.dispatch();
	}

}
