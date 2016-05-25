/**
 * All right is from Author of the file,to be explained in comming days.
 * Sep 22, 2012
 */
package org.cellang.webframework.client.event;

import org.cellang.webframework.client.event.Event.EventHandlerI;
import org.cellang.webframework.client.lang.WebObject;

import com.google.gwt.dev.ui.UiEvent.Type;

/**
 * @author wu
 * 
 */
public interface EventDispatcherI {

	public <E extends Event> void addHandler(WebObject src, EventHandlerI<E> l);

	public <E extends Event> void addHandler(WebObject src, Type<E> ec, EventHandlerI<E> l);

	public void dispatch(Event e);
}
