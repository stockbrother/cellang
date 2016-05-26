/**
 * Jun 29, 2012
 */
package org.cellang.clwt.core.client.event;

import org.cellang.clwt.core.client.lang.WebObject;

/**
 * @author wu
 * 
 */
public class ClickEvent extends Event {

	public static final Type<ClickEvent> TYPE = new Type<ClickEvent>("click");

	/** */
	public ClickEvent(WebObject src) {
		super(TYPE, src);
	}

}
