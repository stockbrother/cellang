/**
 *  Dec 21, 2012
 */
package org.cellang.clwt.core.client.event;

import org.cellang.clwt.core.client.data.MessageData;
import org.cellang.clwt.core.client.transfer.Endpoint;

/**
 * @author wuzhen
 * 
 */
public class EndpointEvent extends Event {

	public static final Type<EndpointEvent> TYPE = new Type<EndpointEvent>("endpoint");

	/**
	 * @param type
	 */
	public EndpointEvent(Type<? extends EndpointEvent> type, Endpoint source) {
		super(type, source);

	}

	protected EndpointEvent(Type<? extends EndpointEvent> type, Endpoint source, MessageData md) {
		super(type, source, md);
	}

	public Endpoint getEndPoint() {
		return (Endpoint) this.source;
	}

	public Endpoint getChannel() {
		return (Endpoint) this.source;
	}

}
