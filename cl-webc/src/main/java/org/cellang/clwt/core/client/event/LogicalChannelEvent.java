/**
 *  Dec 21, 2012
 */
package org.cellang.clwt.core.client.event;

import org.cellang.clwt.core.client.data.MessageData;
import org.cellang.clwt.core.client.transfer.LogicalChannel;

/**
 * @author wuzhen
 * 
 */
public class LogicalChannelEvent extends Event {

	public static final Type<LogicalChannelEvent> TYPE = new Type<LogicalChannelEvent>("endpoint");

	/**
	 * @param type
	 */
	public LogicalChannelEvent(Type<? extends LogicalChannelEvent> type, LogicalChannel source) {
		super(type, source);

	}

	protected LogicalChannelEvent(Type<? extends LogicalChannelEvent> type, LogicalChannel source, MessageData md) {
		super(type, source, md);
	}

	public LogicalChannel getEndPoint() {
		return (LogicalChannel) this.source;
	}

	public LogicalChannel getChannel() {
		return (LogicalChannel) this.source;
	}

}
