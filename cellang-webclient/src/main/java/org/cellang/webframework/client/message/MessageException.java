/**
 *  Dec 24, 2012
 */
package org.cellang.webframework.client.message;

/**
 * @author wuzhen
 * 
 */
public class MessageException {

	protected Throwable exception;
	protected MessageDataWrapper messageData;

	public Throwable getException() {
		return exception;
	}

	public MessageDataWrapper getMessageData() {
		return messageData;
	}

	public MessageException(Throwable t, MessageDataWrapper md) {
		this.exception = t;
		this.messageData = md;
	}
}
