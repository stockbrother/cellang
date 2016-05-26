/**
 * Jun 29, 2012
 */
package org.cellang.clwt.core.client.event;

import org.cellang.clwt.core.client.lang.WebObject;

/**
 * @author wu
 * 
 */
public class BlurEvent extends Event {

	public static final Type<BlurEvent> TYPE = new Type<BlurEvent>("blur");

	/** */
	public BlurEvent(WebObject src) {
		super(TYPE, src);
	}

}
