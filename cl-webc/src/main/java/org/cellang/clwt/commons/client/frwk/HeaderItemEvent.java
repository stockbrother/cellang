/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 2, 2013
 */
package org.cellang.clwt.commons.client.frwk;

import org.cellang.clwt.core.client.event.Event;
import org.cellang.clwt.core.client.lang.Path;
import org.cellang.clwt.core.client.lang.WebObject;

/**
 * @author wu
 * 
 */
public class HeaderItemEvent extends Event {

	public static final Type<HeaderItemEvent> TYPE = new Type<HeaderItemEvent>("header-item");

	/**
	 * @param type
	 */
	public HeaderItemEvent(WebObject src, Path path) {
		super(TYPE, src, path);
	}

}
