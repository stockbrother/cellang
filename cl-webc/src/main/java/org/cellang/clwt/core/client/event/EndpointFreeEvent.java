/**
 * All right is from Author of the file,to be explained in comming days.
 * Apr 4, 2013
 */
package org.cellang.clwt.core.client.event;

import org.cellang.clwt.core.client.transferpoint.TransferPoint;

/**
 * @author wu
 * 
 */
public class EndpointFreeEvent extends EndpointEvent {

	public static final Event.Type<EndpointFreeEvent> TYPE = new Event.Type<EndpointFreeEvent>(
			EndpointEvent.TYPE, "free");

	/**
	 * @param type
	 */
	public EndpointFreeEvent(TransferPoint src) {
		super(TYPE, src);
	}

}
