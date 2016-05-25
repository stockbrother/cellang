/**
 *  Dec 24, 2012
 */
package org.cellang.webframework.client.message;

import org.cellang.webframework.client.lang.Handler;

/**
 * @author wuzhen
 * 
 */
public interface MessageExceptionHandlerI extends Handler<MessageException> {

	public void handle(MessageException me);

}
