/**
 * Jun 29, 2012
 */
package org.cellang.webcore.client.event;

import org.cellang.webcore.client.lang.WebObject;

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
