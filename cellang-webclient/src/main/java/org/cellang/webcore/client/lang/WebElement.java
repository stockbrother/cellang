/**
 * Jun 13, 2012
 */
package org.cellang.webcore.client.lang;

import org.cellang.webcore.client.core.ElementWrapper;
import org.cellang.webcore.client.gwtbridge.GwtHandlerI;

import com.google.gwt.event.dom.client.DomEvent;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.Element;

/**
 * @author wu
 * 
 */
public interface WebElement extends WebObject {
	
	public Element getElement();

	public ElementWrapper getElementWrapper();

	public boolean isVisible();

	public void setVisible(boolean v);

	public <E extends GwtEvent<H>, H extends EventHandler> HandlerRegistration addGwtEventHandler(
			DomEvent.Type<H> type, GwtHandlerI<E, H> eh);

	@Deprecated
	// should not depends on GWT event?
	public <H extends EventHandler> HandlerRegistration addGwtHandler(
			DomEvent.Type<H> type, final H handler);

	@Deprecated
	// use ElementWrapper.click()
	public void _click();

}
