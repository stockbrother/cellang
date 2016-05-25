/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 17, 2012
 */
package org.cellang.webframework.client.gwtbridge;

import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;

/**
 * @author wu
 * 
 */
public abstract class GwtMouseUpHandler extends
		AbstractGwtHandler<MouseUpEvent, MouseUpHandler> implements
		MouseUpHandler {
	@Override
	public void onMouseUp(MouseUpEvent evt) {
		super.handle(evt);
	}
}
