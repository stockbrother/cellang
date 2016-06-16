/**
 * Jul 20, 2012
 */
package org.cellang.clwt.core.client.event;

import org.cellang.clwt.core.client.ClientObject;
import org.cellang.clwt.core.client.transfer.LogicalChannel;

/**
 * @author wu <br>
 *         After client start:<br>
 *         a)Endpoint is established and message able to be transfer between
 *         client and server.Endpoint is raw message channel.<br>
 *         b)After a), clientIsReady message send to server, so server will
 *         assign an clientId and terminalId(logic channel) for this client. c)A
 *         logic channel is build on the endpoint.
 * 
 *         //TODO rename to logicalchannel is ok event.
 */
public class ClientStartedEvent extends ClientEvent {
	public static Type<ClientStartedEvent> TYPE = new Type<ClientStartedEvent>(ClientEvent.TYPE, "started");

	/** */
	public ClientStartedEvent(ClientObject client) {
		super(TYPE, client);
	}

	public ClientObject getClient() {
		return (ClientObject) this.source;
	}

	public LogicalChannel getLogicalChannel() {
		return this.getClient().getLogicalChannel(true);
	}

}