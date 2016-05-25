/**
 * Jun 29, 2012
 */
package org.cellang.webframework.client.event;

import org.cellang.webframework.client.lang.WebObject;

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
