/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 23, 2012
 */
package org.cellang.webcore.client.event;

import org.cellang.webcore.client.transferpoint.TransferPoint;


/**
 * @author wu
 * 
 */
public class EndpointBondEvent extends EndpointEvent {

	public static final Type<EndpointBondEvent> TYPE = new Type<EndpointBondEvent>(EndpointEvent.TYPE, "bond");

	protected String sessionId;

	/**
	 * @param type
	 */
	public EndpointBondEvent(TransferPoint c, String sessionId) {
		super(TYPE, c);
	}

	/**
	 * @return the sessionId
	 */
	public String getSessionId() {
		return sessionId;
	}

}
