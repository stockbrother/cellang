/**
 *  Dec 24, 2012
 */
package org.cellang.clwt.core.client.message;

/**
 * @author wuzhen
 * 
 */
public class MessageException {

	protected Throwable exception;
	protected MsgWrapper messageData;

	public Throwable getException() {
		return exception;
	}

	public MsgWrapper getMessageData() {
		return messageData;
	}

	public MessageException(Throwable t, MsgWrapper md) {
		this.exception = t;
		this.messageData = md;
	}
}
