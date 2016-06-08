/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 23, 2012
 */
package org.cellang.clwt.core.client.event;

import org.cellang.clwt.core.client.transfer.LogicalChannel;

/**
 * @author wu
 * 
 */
public class LogicalChannelUnbondEvent extends LogicalChannelEvent {

	public static final Type<LogicalChannelUnbondEvent> TYPE = new Type<LogicalChannelUnbondEvent>(LogicalChannelEvent.TYPE, "unbond");

	/**
	 * @param type
	 */
	public LogicalChannelUnbondEvent(LogicalChannel c) {
		super(TYPE, c);
	}

}
