/**
 *  Dec 21, 2012
 */
package org.cellang.clwt.core.client.event;

import org.cellang.clwt.core.client.transfer.Endpoint;


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
	public EndpointOpenEvent(Endpoint c) {
		super(TYPE, c);
	}

}
