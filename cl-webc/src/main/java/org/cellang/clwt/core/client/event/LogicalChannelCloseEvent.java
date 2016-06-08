/**
 *  Dec 21, 2012
 */
package org.cellang.clwt.core.client.event;

import org.cellang.clwt.core.client.transfer.LogicalChannel;

/**
 * @author wuzhen
 * 
 */
public class LogicalChannelCloseEvent extends LogicalChannelEvent {

	public static final Type<LogicalChannelCloseEvent> TYPE = new Type<LogicalChannelCloseEvent>(LogicalChannelEvent.TYPE,
			"close");

	protected String code;

	/**
	 * @param type
	 */
	public LogicalChannelCloseEvent(LogicalChannel c, String code, String reason) {
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
