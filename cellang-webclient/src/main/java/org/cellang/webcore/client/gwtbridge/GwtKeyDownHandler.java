/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 17, 2012
 */
package org.cellang.webcore.client.gwtbridge;

import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;

/**
 * @author wu
 * 
 */
public abstract class GwtKeyDownHandler extends
		AbstractGwtHandler<KeyDownEvent, KeyDownHandler> implements KeyDownHandler {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.google.gwt.event.dom.client.MouseUpHandler#onMouseUp(com.google.gwt
	 * .event.dom.client.MouseUpEvent)
	 */
	@Override
	public void onKeyDown(KeyDownEvent event) {
		this.handle(event);// o
	}

}
