/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 17, 2012
 */
package org.cellang.clwt.core.client.gwtbridge;

import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;

/**
 * @author wu
 * 
 */
public abstract class GwtMouseOverHandler extends AbstractGwtHandler<MouseOverEvent, MouseOverHandler>
		implements MouseOverHandler {
	@Override
	public void onMouseOver(MouseOverEvent evt) {
		super.handle(evt);
	}
}
