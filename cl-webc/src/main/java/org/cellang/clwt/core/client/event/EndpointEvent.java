/**
 *  Dec 21, 2012
 */
package org.cellang.clwt.core.client.event;

import org.cellang.clwt.core.client.data.MessageData;
import org.cellang.clwt.core.client.transferpoint.TransferPoint;

/**
 * @author wuzhen
 * 
 */
public class EndpointEvent extends Event {

	public static final Type<EndpointEvent> TYPE = new Type<EndpointEvent>("endpoint");

	/**
	 * @param type
	 */
	public EndpointEvent(Type<? extends EndpointEvent> type, TransferPoint source) {
		super(type, source);

	}

	protected EndpointEvent(Type<? extends EndpointEvent> type, TransferPoint source, MessageData md) {
		super(type, source, md);
	}

	public TransferPoint getEndPoint() {
		return (TransferPoint) this.source;
	}

	public TransferPoint getChannel() {
		return (TransferPoint) this.source;
	}

}
