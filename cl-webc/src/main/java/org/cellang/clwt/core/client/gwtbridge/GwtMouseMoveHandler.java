/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 17, 2012
 */
package org.cellang.clwt.core.client.gwtbridge;

import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;

/**
 * @author wu
 * 
 */
public abstract class GwtMouseMoveHandler extends
		AbstractGwtHandler<MouseMoveEvent, MouseMoveHandler> implements
		MouseMoveHandler {
	@Override
	public void onMouseMove(MouseMoveEvent evt) {
		super.handle(evt);
	}
}
