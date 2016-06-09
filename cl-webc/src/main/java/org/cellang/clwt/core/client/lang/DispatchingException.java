/**
 *  Dec 24, 2012
 */
package org.cellang.clwt.core.client.lang;

/**
 * @author wuzhen
 * 
 */
public class DispatchingException {

	protected Throwable exception;
	protected Object object;

	public Throwable getException() {
		return exception;
	}

	public Object getMessageData() {
		return object;
	}

	public DispatchingException(Throwable t, Object md) {
		this.exception = t;
		this.object = md;
	}
}
