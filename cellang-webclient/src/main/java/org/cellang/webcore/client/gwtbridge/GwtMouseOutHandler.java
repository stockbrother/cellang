/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 17, 2012
 */
package org.cellang.webcore.client.gwtbridge;

import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;

/**
 * @author wu
 * 
 */
public abstract class GwtMouseOutHandler extends AbstractGwtHandler<MouseOutEvent, MouseOutHandler> implements
		MouseOutHandler {
	@Override
	public void onMouseOut(MouseOutEvent evt) {
		super.handle(evt);
	}
}
