/**
 * Jul 20, 2012
 */
package org.cellang.clwt.core.client.event;

import org.cellang.clwt.core.client.WebClient;
import org.cellang.clwt.core.client.transfer.Endpoint;

/**
 * @author wu <br>
 *         After client start, a Endpoint available.
 */
public class AfterClientStartEvent extends ClientEvent {
	public static Type<AfterClientStartEvent> TYPE = new Type<AfterClientStartEvent>(ClientEvent.TYPE, "started");

	/** */
	public AfterClientStartEvent(WebClient client) {
		super(TYPE, client);
	}

	public WebClient getClient() {
		return (WebClient) this.source;
	}

	public Endpoint getEndPoint() {
		return this.getClient().getEndpoint(true);
	}

}