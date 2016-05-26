/**
 * Jun 29, 2012
 */
package org.cellang.clwt.core.client.event;

import org.cellang.clwt.core.client.lang.WebObject;

/**
 * @author wu
 * 
 */
public class KeyUpEvent extends KeyCodeEvent {

	public static final Type<KeyUpEvent> TYPE = new Type<KeyUpEvent>("key-up");

	/** */
	public KeyUpEvent(WebObject src, int keyCode, boolean ctlKey) {
		super(TYPE, src, keyCode, ctlKey);
	}

}
