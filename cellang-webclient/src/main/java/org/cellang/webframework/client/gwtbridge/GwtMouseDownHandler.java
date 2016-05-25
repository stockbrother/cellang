/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 17, 2012
 */
package org.cellang.webframework.client.gwtbridge;

import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;

/**
 * @author wu
 * 
 */
public abstract class GwtMouseDownHandler extends
		AbstractGwtHandler<MouseDownEvent, MouseDownHandler> implements
		MouseDownHandler {
	@Override
	public void onMouseDown(MouseDownEvent evt) {
		super.handle(evt);
	}
}
