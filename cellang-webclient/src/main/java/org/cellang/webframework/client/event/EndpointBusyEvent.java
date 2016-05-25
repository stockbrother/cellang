/**
 * All right is from Author of the file,to be explained in comming days.
 * Apr 4, 2013
 */
package org.cellang.webframework.client.event;

import org.cellang.webframework.client.transferpoint.TransferPoint;

/**
 * @author wu
 * 
 */
public class EndpointBusyEvent extends EndpointEvent {

	public static final Event.Type<EndpointBusyEvent> TYPE = new Event.Type<EndpointBusyEvent>(
			EndpointEvent.TYPE, "busy");

	/**
	 * @param type
	 */
	public EndpointBusyEvent(TransferPoint src) {
		super(TYPE, src);
	}

}
