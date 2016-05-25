/**
 *  Jan 16, 2013
 */
package org.cellang.webframework.client.core;

import org.cellang.webframework.client.WebException;
import org.cellang.webframework.client.lang.Handler;
import org.cellang.webframework.client.logger.WebLoggerFactory;
import org.cellang.webframework.client.logger.WebLogger;

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
	 * @see com.fs.uicore.api.gwt.client.HandlerI#handle(java.lang.Object)
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
