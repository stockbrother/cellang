/**
 * Jul 20, 2012
 */
package org.cellang.webcore.client.event;

import org.cellang.webcore.client.WebClient;

/**
 * @author wu
 * 
 * 
 */
public class ClientConnectLostEvent extends ClientEvent {
	public static Type<ClientConnectLostEvent> TYPE = new Type<ClientConnectLostEvent>(ClientEvent.TYPE,
			"connection-lost");

	/** */
	public ClientConnectLostEvent(WebClient client) {
		super(TYPE, client);
	}

}