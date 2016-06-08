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
public class LogicalChannelBusyEvent extends LogicalChannelEvent {

	public static final Event.Type<LogicalChannelBusyEvent> TYPE = new Event.Type<LogicalChannelBusyEvent>(
			LogicalChannelEvent.TYPE, "busy");

	/**
	 * @param type
	 */
	public LogicalChannelBusyEvent(LogicalChannel src) {
		super(TYPE, src);
	}

}
