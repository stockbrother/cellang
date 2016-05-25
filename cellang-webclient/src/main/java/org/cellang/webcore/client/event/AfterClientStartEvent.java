/**
 * Jul 20, 2012
 */
package org.cellang.webcore.client.event;

import org.cellang.webcore.client.WebClient;
import org.cellang.webcore.client.transferpoint.TransferPoint;

/**
 * @author wu
 * 
 */
public class AfterClientStartEvent extends ClientEvent {
	public static Type<AfterClientStartEvent> TYPE = new Type<AfterClientStartEvent>(ClientEvent.TYPE,
			"started");

	/** */
	public AfterClientStartEvent(WebClient client) {
		super(TYPE, client);
	}

	public WebClient getClient() {
		return (WebClient) this.source;
	}

	public TransferPoint getEndPoint() {
		return this.getClient().getEndpoint(true);
	}

}