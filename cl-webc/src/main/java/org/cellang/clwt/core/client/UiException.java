/**
 * Jun 23, 2012
 */
package org.cellang.clwt.core.client;

/**
 * @author wu
 * 
 */
public class UiException extends RuntimeException {

	public UiException() {
		this((String) null);
	}

	public UiException(String msg) {
		this(msg, null);
	}

	public UiException(Throwable t) {
		this(null, t);
	}

	public UiException(String msg, Throwable t) {
		super(msg, t);
	}

	/**
	 * Dec 21, 2012
	 */
	public static RuntimeException toRtE(Throwable t) {
		if (t instanceof RuntimeException) {
			return (RuntimeException) t;
		} else {
			return new UiException(t);
		}
	}

}
