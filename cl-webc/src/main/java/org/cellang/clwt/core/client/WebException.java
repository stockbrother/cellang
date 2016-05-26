/**
 * Jun 23, 2012
 */
package org.cellang.clwt.core.client;

/**
 * @author wu
 * 
 */
public class WebException extends RuntimeException {

	public WebException() {
		this((String) null);
	}

	public WebException(String msg) {
		this(msg, null);
	}

	public WebException(Throwable t) {
		this(null, t);
	}

	public WebException(String msg, Throwable t) {
		super(msg, t);
	}

	/**
	 * Dec 21, 2012
	 */
	public static RuntimeException toRtE(Throwable t) {
		if (t instanceof RuntimeException) {
			return (RuntimeException) t;
		} else {
			return new WebException(t);
		}
	}

}
