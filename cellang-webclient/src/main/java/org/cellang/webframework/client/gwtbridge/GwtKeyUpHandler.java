/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 17, 2012
 */
package org.cellang.webframework.client.gwtbridge;

import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;

/**
 * @author wu
 * 
 */
public abstract class GwtKeyUpHandler extends
		AbstractGwtHandler<KeyUpEvent, KeyUpHandler> implements KeyUpHandler {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.google.gwt.event.dom.client.MouseUpHandler#onMouseUp(com.google.gwt
	 * .event.dom.client.MouseUpEvent)
	 */
	@Override
	public void onKeyUp(KeyUpEvent event) {
		this.handle(event);// o
	}

}
