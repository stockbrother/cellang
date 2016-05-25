/**
 *  
 */
package org.cellang.webframework.client;

import org.cellang.webframework.client.event.Event;
import org.cellang.webframework.client.event.Event.EventHandlerI;
import org.cellang.webframework.client.lang.WebModuleFactory;
import org.cellang.webframework.client.lang.WebModule;

import com.google.gwt.core.shared.GWT;

/**
 * @author wu Test support.
 */
public abstract class ClientLoader {
	
	public abstract WebModuleFactory getOrLoadClient(WebModule[] spis, EventHandlerI<Event> l);

	public abstract WebModuleFactory loadClient(WebModule[] spis, EventHandlerI<Event> l);

}
