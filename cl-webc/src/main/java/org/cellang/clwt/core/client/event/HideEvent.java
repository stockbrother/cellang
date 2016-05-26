/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 5, 2012
 */
package org.cellang.clwt.core.client.event;

import org.cellang.clwt.core.client.lang.WebObject;

/**
 * @author wu
 * 
 */
public class HideEvent extends Event {

	public static final Type<HideEvent> TYPE = new Type<HideEvent>("hide");

	/**
	 * @param type
	 */
	public HideEvent(WebObject src) {
		super(TYPE, src);
	}

}
