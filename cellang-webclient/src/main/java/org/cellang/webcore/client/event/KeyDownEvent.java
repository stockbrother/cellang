/**
 * Jun 29, 2012
 */
package org.cellang.webcore.client.event;

import org.cellang.webcore.client.lang.WebObject;

/**
 * @author wu
 * 
 */
public class KeyDownEvent extends KeyCodeEvent {

	public static final Type<KeyDownEvent> TYPE = new Type<KeyDownEvent>("key-down");

	/** */
	public KeyDownEvent(WebObject src, int keyCode, boolean ctr) {
		super(TYPE, src, keyCode, ctr);
	}

}
