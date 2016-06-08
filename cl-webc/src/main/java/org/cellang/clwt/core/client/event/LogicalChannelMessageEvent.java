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
public class LogicalChannelMessageEvent extends LogicalChannelEvent {

	public static final Type<LogicalChannelMessageEvent> TYPE = new Type<LogicalChannelMessageEvent>(LogicalChannelEvent.TYPE,
			"message");

	/**
	 * @param type
	 */
	public LogicalChannelMessageEvent(LogicalChannel c, MessageData md) {
		super(TYPE, c, md);
	}

}
