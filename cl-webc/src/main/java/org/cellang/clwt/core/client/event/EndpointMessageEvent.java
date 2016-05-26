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
public class EndpointMessageEvent extends EndpointEvent {

	public static final Type<EndpointMessageEvent> TYPE = new Type<EndpointMessageEvent>(EndpointEvent.TYPE,
			"message");

	/**
	 * @param type
	 */
	public EndpointMessageEvent(TransferPoint c, MessageData md) {
		super(TYPE, c, md);
	}

}
