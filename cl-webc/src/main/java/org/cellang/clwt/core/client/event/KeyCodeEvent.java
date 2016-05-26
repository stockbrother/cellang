/**
 * Jun 29, 2012
 */
package org.cellang.clwt.core.client.event;

import org.cellang.clwt.core.client.lang.WebObject;

import com.google.gwt.event.dom.client.KeyCodes;

/**
 * @author wu
 * 
 */
public abstract class KeyCodeEvent extends Event {

	protected int keyCode;
	
	protected boolean ctlKey;
	/** */
	public KeyCodeEvent(Type<? extends KeyCodeEvent> type,WebObject src, int keyCode, boolean ctlKey) {
		super(type, src);
		this.keyCode = keyCode;
		this.ctlKey = ctlKey;
	}
	
	public int getNativeKeyCode() {
		return this.keyCode;
	}
	
	public boolean isEnter(){
		return KeyCodes.KEY_ENTER == this.keyCode;
	}
	
	public boolean isCtlKey(){
		return this.ctlKey;
	}

}
