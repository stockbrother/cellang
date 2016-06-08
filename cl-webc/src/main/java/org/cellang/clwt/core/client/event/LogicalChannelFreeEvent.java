/**
 * All right is from Author of the file,to be explained in comming days.
 * Apr 4, 2013
 */
package org.cellang.clwt.core.client.event;

import org.cellang.clwt.core.client.transfer.LogicalChannel;

/**
 * @author wu
 * 
 */
public class LogicalChannelFreeEvent extends LogicalChannelEvent {

	public static final Event.Type<LogicalChannelFreeEvent> TYPE = new Event.Type<LogicalChannelFreeEvent>(
			LogicalChannelEvent.TYPE, "free");

	/**
	 * @param type
	 */
	public LogicalChannelFreeEvent(LogicalChannel src) {
		super(TYPE, src);
	}

}
