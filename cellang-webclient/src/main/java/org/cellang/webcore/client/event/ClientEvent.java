/**
 * Jul 20, 2012
 */
package org.cellang.webcore.client.event;

import org.cellang.webcore.client.WebClient;

/**
 * @author wu
 * 
 */
public abstract class ClientEvent extends Event {
	public static Type<ClientEvent> TYPE = new Type<ClientEvent>("client");

	/** */
	public ClientEvent(Type<? extends ClientEvent> type, WebClient client) {
		super(type, client);
	}

}