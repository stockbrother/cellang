/**
 *  Dec 21, 2012
 */
package org.cellang.clwt.core.client.event;

import org.cellang.clwt.core.client.data.ErrorInfoData;
import org.cellang.clwt.core.client.data.ErrorInfosData;
import org.cellang.clwt.core.client.transfer.LogicalChannel;

/**
 * @author wuzhen
 * 
 */
public class LogicalChannelErrorEvent extends LogicalChannelEvent {

	public static final Type<LogicalChannelErrorEvent> TYPE = new Type<LogicalChannelErrorEvent>(LogicalChannelEvent.TYPE, "error");

	protected ErrorInfosData errors = new ErrorInfosData();

	/**
	 * @param type
	 */
	public LogicalChannelErrorEvent(LogicalChannel c, String message) {
		super(TYPE, c);
		this.errors.add(new ErrorInfoData("unknow", message));
	}

	/**
	 * @return the errors
	 */
	public ErrorInfosData getErrors() {
		return errors;
	}

}
