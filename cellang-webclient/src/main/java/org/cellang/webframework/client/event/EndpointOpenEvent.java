/**
 *  Dec 21, 2012
 */
package org.cellang.webframework.client.event;

import org.cellang.webframework.client.transferpoint.TransferPoint;


/**
 * @author wuzhen
 * 
 */
public class EndpointOpenEvent extends EndpointEvent {

	public static final Type<EndpointOpenEvent> TYPE = new Type<EndpointOpenEvent>(
			EndpointEvent.TYPE, "open");

	/**
	 * @param type
	 */
	public EndpointOpenEvent(TransferPoint c) {
		super(TYPE, c);
	}

}
