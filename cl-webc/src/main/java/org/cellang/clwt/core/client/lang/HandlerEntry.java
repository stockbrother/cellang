/**
 *  Dec 24, 2012
 */
package org.cellang.clwt.core.client.lang;

import org.cellang.clwt.core.client.logger.WebLogger;
import org.cellang.clwt.core.client.logger.WebLoggerFactory;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;

/**
 * @author wuzhen
 * 
 */
public final class HandlerEntry {

	private static final WebLogger LOG = WebLoggerFactory.getLogger(HandlerEntry.class);

	protected Path path;
	protected DispatcherImpl dispatcher;
	protected Handler handlers;

	protected boolean strict;

	public HandlerEntry(DispatcherImpl dispatcher, Path p, boolean includeSubPath, Handler hdls) {
		this.dispatcher = dispatcher;
		this.path = p;
		this.strict = includeSubPath;
		this.handlers = hdls;
	}

	public boolean tryHandle(boolean dely, Path p, final Object md) {

		boolean isMatch = this.isMatch(p);
		if (LOG.isTraceEnabled()) {
			LOG.trace("tryHandle,this.path:" + this.path + ",event.path:" + p + ",isMatch:" + isMatch//
					+ ",handlers:" + this.handlers + ",dispatcherName:" + this.dispatcher.name + ",dely:" + dely
					+ ",this.path:" + p + ",message:" + md);
		}

		if (!isMatch) {
			return false;
		}

		if (dely) {
			if (LOG.isTraceEnabled()) {
				LOG.trace("scheduleDeferred,handler:" + this.handlers);
			}
			Scheduler.get().scheduleDeferred(new ScheduledCommand() {

				@Override
				public void execute() {
					HandlerEntry.this.doHandle(md);

				}
			});
		} else {
			if (LOG.isTraceEnabled()) {
				LOG.trace("no deferred handle,handler:" + this.handlers);
			}
			HandlerEntry.this.doHandle(md);
		}
		return true;
	}

	protected void doHandle(Object md) {
		if (LOG.isTraceEnabled()) {
			LOG.trace("doHandle," + this.dispatcher.name + ",doHandle,message:" + md + ",handlers:" + handlers);//
		}

		try {
			this.handlers.handle(md);
		} catch (Throwable t) {
			LOG.error("dispatcher:" + this.dispatcher.name + ",handler exception for msg:" + md, t);
		}
	}

	public boolean isMatch(Path p) {
		if (this.strict) {
			return this.path.equals(p);//
		} else {
			return this.path.isSubPath(p, true);
		}
	}

	/*
	 * Jan 1, 2013
	 */
	@Override
	public String toString() {
		return "{path:" + this.path + ",handler:" + this.handlers + "}";
	}

}
