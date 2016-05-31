/**
 *  Jan 16, 2013
 */
package org.cellang.clwt.core.client.core;

import org.cellang.clwt.core.client.UiException;
import org.cellang.clwt.core.client.lang.Handler;
import org.cellang.clwt.core.client.logger.WebLogger;
import org.cellang.clwt.core.client.logger.WebLoggerFactory;

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
			throw UiException.toRtE(e);
		}
	}

}
