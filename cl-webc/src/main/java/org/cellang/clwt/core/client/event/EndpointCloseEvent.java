/**
 *  Dec 21, 2012
 */
package org.cellang.clwt.core.client.event;

import org.cellang.clwt.core.client.transferpoint.TransferPoint;

/**
 * @author wuzhen
 * 
 */
public class EndpointCloseEvent extends EndpointEvent {

	public static final Type<EndpointCloseEvent> TYPE = new Type<EndpointCloseEvent>(EndpointEvent.TYPE,
			"close");

	protected String code;

	/**
	 * @param type
	 */
	public EndpointCloseEvent(TransferPoint c, String code, String reason) {
		super(TYPE, c);
		this.code = code;
		this.reason = reason;
	}

	public String getCode() {
		return code;
	}

	public String getReason() {
		return reason;
	}

	protected String reason;
}
