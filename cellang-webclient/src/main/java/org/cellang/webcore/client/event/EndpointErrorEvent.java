/**
 *  Dec 21, 2012
 */
package org.cellang.webcore.client.event;

import org.cellang.webcore.client.data.ErrorInfoData;
import org.cellang.webcore.client.data.ErrorInfosData;
import org.cellang.webcore.client.transferpoint.TransferPoint;

/**
 * @author wuzhen
 * 
 */
public class EndpointErrorEvent extends EndpointEvent {

	public static final Type<EndpointErrorEvent> TYPE = new Type<EndpointErrorEvent>(EndpointEvent.TYPE, "error");

	protected ErrorInfosData errors = new ErrorInfosData();

	/**
	 * @param type
	 */
	public EndpointErrorEvent(TransferPoint c, String message) {
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
