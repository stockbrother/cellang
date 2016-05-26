/**
 * Jul 20, 2012
 */
package org.cellang.clwt.core.client.event;

import org.cellang.clwt.core.client.WebClient;

/**
 * @author wu
 * 
 * 
 */
public class ClientStartFailureEvent extends ClientEvent {
	public static Type<ClientStartFailureEvent> TYPE = new Type<ClientStartFailureEvent>(
			ClientEvent.TYPE, "start-failure");

	/** */
	public ClientStartFailureEvent(WebClient client) {
		super(TYPE, client);
	}

}