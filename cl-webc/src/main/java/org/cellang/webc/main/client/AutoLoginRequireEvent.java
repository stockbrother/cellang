/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 11, 2012
 */
package org.cellang.webc.main.client;

import org.cellang.clwt.core.client.event.Event;
import org.cellang.clwt.core.client.lang.WebObject;

/**
 * @author wu
 * 
 * 
 */
public class AutoLoginRequireEvent extends Event {

	public static final Type<AutoLoginRequireEvent> TYPE = new Type<AutoLoginRequireEvent>("auto-login-request");

	/**
	 * @param src
	 * @param code
	 */
	public AutoLoginRequireEvent(WebObject src) {
		super(TYPE, src);
	}

}
