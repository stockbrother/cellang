/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 17, 2012
 */
package org.cellang.webcore.client.gwtbridge;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;

/**
 * @author wu
 * 
 */
public abstract class GwtBlurHandler extends
		AbstractGwtHandler<BlurEvent, BlurHandler> implements BlurHandler {
	
	@Override
	public void onBlur(BlurEvent event) {
		this.handle(event);// o
	}

}
