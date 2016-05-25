/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 17, 2012
 */
package org.cellang.webframework.client.gwtbridge;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;

/**
 * @author wu
 * 
 */
public abstract class GwtChangeHandler extends
		AbstractGwtHandler<ChangeEvent, ChangeHandler> implements ChangeHandler {

	/*
	 * Nov 17, 2012
	 */
	@Override
	public void onChange(ChangeEvent changeevent) {
		//
		this.handle(changeevent);
	}

}
