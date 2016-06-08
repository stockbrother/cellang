/**
 *  Dec 21, 2012
 */
package org.cellang.clwt.core.client.event;

import org.cellang.clwt.core.client.transfer.LogicalChannel;


/**
 * @author wuzhen
 * 
 */
public class LogicalChannelOpenEvent extends LogicalChannelEvent {

	public static final Type<LogicalChannelOpenEvent> TYPE = new Type<LogicalChannelOpenEvent>(
			LogicalChannelEvent.TYPE, "open");

	/**
	 * @param type
	 */
	public LogicalChannelOpenEvent(LogicalChannel c) {
		super(TYPE, c);
	}

}
