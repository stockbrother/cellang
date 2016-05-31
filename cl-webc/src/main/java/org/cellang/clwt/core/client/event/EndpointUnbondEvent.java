/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 23, 2012
 */
package org.cellang.clwt.core.client.event;

import org.cellang.clwt.core.client.transfer.Endpoint;

/**
 * @author wu
 * 
 */
public class EndpointUnbondEvent extends EndpointEvent {

	public static final Type<EndpointUnbondEvent> TYPE = new Type<EndpointUnbondEvent>(EndpointEvent.TYPE, "unbond");

	/**
	 * @param type
	 */
	public EndpointUnbondEvent(Endpoint c) {
		super(TYPE, c);
	}

}
