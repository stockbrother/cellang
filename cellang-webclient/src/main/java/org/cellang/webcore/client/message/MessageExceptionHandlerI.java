/**
 *  Dec 24, 2012
 */
package org.cellang.webcore.client.message;

import org.cellang.webcore.client.lang.Handler;

/**
 * @author wuzhen
 * 
 */
public interface MessageExceptionHandlerI extends Handler<MessageException> {

	public void handle(MessageException me);

}
