/**
 * Jun 29, 2012
 */
package org.cellang.clwt.core.client.event;

import org.cellang.clwt.core.client.lang.WebObject;

/**
 * @author wu
 * 
 */
public class FocusEvent extends Event {

	public static final Type<FocusEvent> TYPE = new Type<FocusEvent>("focus");

	/** */
	public FocusEvent(WebObject src) {
		super(TYPE, src);
	}

}
