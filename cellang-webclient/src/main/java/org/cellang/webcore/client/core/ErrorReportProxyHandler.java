/**
 *  Jan 16, 2013
 */
package org.cellang.webcore.client.core;

import org.cellang.webcore.client.WebException;
import org.cellang.webcore.client.lang.Handler;
import org.cellang.webcore.client.logger.WebLogger;
import org.cellang.webcore.client.logger.WebLoggerFactory;

/**
 * @author wuzhen
 * 
 */
public class ErrorReportProxyHandler<T> implements Handler<T> {

	private static final WebLogger LOG = WebLoggerFactory.getLogger(ErrorReportProxyHandler.class);

	private Handler<T> target;

	public ErrorReportProxyHandler(Handler<T> target) {
		this.target = target;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see HandlerI#handle(java.lang.Object)
	 */
	@Override
	public void handle(T t) {
		try {
			this.target.handle(t);
		} catch (Throwable e) {
			LOG.error("handler error in handler:" + this.target, e);
			throw WebException.toRtE(e);
		}
	}

}
