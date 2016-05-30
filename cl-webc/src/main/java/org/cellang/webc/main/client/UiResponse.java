/**
 * Jun 12, 2012
 */
package org.cellang.webc.main.client;

import org.cellang.clwt.core.client.data.MessageData;
import org.cellang.clwt.core.client.message.MsgWrapper;

/**
 * @author wuzhen
 * 
 */
public class UiResponse extends MsgWrapper {

	public static final String ERROR_INFO_S = "_ERROR_INFO_S";// NOTE must same
																// as
																// ResponseImpl
																// in server
																// side.

	protected MsgWrapper request;

	public UiResponse(MessageData md) {
		super(md);

	}

}
