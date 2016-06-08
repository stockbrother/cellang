/**
 * Jul 20, 2012
 */
package org.cellang.clwt.core.client.event;

import org.cellang.clwt.core.client.ClientObject;

/**
 * @author wu <br>
 *        
 */
public class ClientStartingEvent extends ClientEvent {
	public static Type<ClientStartingEvent> TYPE = new Type<ClientStartingEvent>(ClientEvent.TYPE, "starting");

	/** */
	public ClientStartingEvent(ClientObject client) {
		super(TYPE, client);
	}

	public ClientObject getClient() {
		return (ClientObject) this.source;
	}

}