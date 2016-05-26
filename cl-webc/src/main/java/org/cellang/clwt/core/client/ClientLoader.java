/**
 *  
 */
package org.cellang.clwt.core.client;

import org.cellang.clwt.core.client.event.Event;
import org.cellang.clwt.core.client.event.Event.EventHandlerI;
import org.cellang.clwt.core.client.lang.Plugin;
import org.cellang.clwt.core.client.lang.Plugins;

/**
 * @author wu Test support.
 */
public abstract class ClientLoader {
	
	public abstract Plugins getOrLoadClient(Plugin[] spis, EventHandlerI<Event> l);

	public abstract Plugins loadClient(Plugin[] spis, EventHandlerI<Event> l);

}
