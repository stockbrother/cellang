/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 23, 2012
 */
package org.cellang.clwt.core.client.event;

import org.cellang.clwt.core.client.transfer.LogicalChannel;

/**
 * @author wu <br>
 *         This event means the channel is authed, and bind to a user.
 */
public class LogicalChannelBondEvent extends LogicalChannelEvent {

	public static final Type<LogicalChannelBondEvent> TYPE = new Type<LogicalChannelBondEvent>(LogicalChannelEvent.TYPE, "bond");

	protected String sessionId;

	/**
	 * @param type
	 */
	public LogicalChannelBondEvent(LogicalChannel c, String sessionId) {
		super(TYPE, c);
	}

	/**
	 * @return the sessionId
	 */
	public String getSessionId() {
		return sessionId;
	}

}
