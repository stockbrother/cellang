/**
 * Jul 20, 2012
 */
package org.cellang.clwt.core.client.event;

import org.cellang.clwt.core.client.ClientObject;

/**
 * @author wu
 * 
 * 
 */
public class ClientConnectLostEvent extends ClientEvent {
	public static Type<ClientConnectLostEvent> TYPE = new Type<ClientConnectLostEvent>(ClientEvent.TYPE,
			"connection-lost");

	/** */
	public ClientConnectLostEvent(ClientObject client) {
		super(TYPE, client);
	}

}