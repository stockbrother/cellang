/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 17, 2012
 */
package org.cellang.webcore.client.gwtbridge;

import com.google.gwt.event.dom.client.ScrollEvent;
import com.google.gwt.event.dom.client.ScrollHandler;

/**
 * @author wu
 * 
 */
public abstract class GwtScrollHandler extends AbstractGwtHandler<ScrollEvent, ScrollHandler> implements
		ScrollHandler {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.google.gwt.event.dom.client.MouseUpHandler#onMouseUp(com.google.gwt
	 * .event.dom.client.MouseUpEvent)
	 */
	@Override
	public void onScroll(ScrollEvent event) {
		this.handle(event);// o
	}

}
