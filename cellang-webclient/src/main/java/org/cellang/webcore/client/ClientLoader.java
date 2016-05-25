/**
 *  
 */
package org.cellang.webcore.client;

import org.cellang.webcore.client.event.Event;
import org.cellang.webcore.client.event.Event.EventHandlerI;
import org.cellang.webcore.client.lang.Implementer;
import org.cellang.webcore.client.lang.ImplementerFactory;

import com.google.gwt.core.shared.GWT;

/**
 * @author wu Test support.
 */
public abstract class ClientLoader {
	
	public abstract ImplementerFactory getOrLoadClient(Implementer[] spis, EventHandlerI<Event> l);

	public abstract ImplementerFactory loadClient(Implementer[] spis, EventHandlerI<Event> l);

}
