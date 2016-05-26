/**
 *  Dec 24, 2012
 */
package org.cellang.clwt.core.client.message;

import org.cellang.clwt.core.client.lang.Handler;

/**
 * @author wuzhen
 * 
 */
public interface MessageExceptionHandlerI extends Handler<MessageException> {

	public void handle(MessageException me);

}
