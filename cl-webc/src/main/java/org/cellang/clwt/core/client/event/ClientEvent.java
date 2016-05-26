/**
 * Jul 20, 2012
 */
package org.cellang.clwt.core.client.event;

import org.cellang.clwt.core.client.WebClient;

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