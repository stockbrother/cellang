/**
 *  Dec 24, 2012
 */
package org.cellang.clwt.core.client.message;

import org.cellang.clwt.core.client.lang.Path;
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

	protected MessageHandlerI handlers;

	protected boolean strict;

	public HandlerEntry(Path p, boolean includeSubPath, MessageHandlerI hdls) {
		this.path = p;
		this.strict = includeSubPath;
		this.handlers = hdls;
	}

	public boolean tryHandle(boolean dely, Path p, final MsgWrapper md) {
		if (!this.isMatch(p)) {
			return false;
		}
		if (dely) {
			Scheduler.get().scheduleDeferred(new ScheduledCommand() {

				@Override
				public void execute() {
					HandlerEntry.this.doHandle(md);

				}
			});
		} else {
			HandlerEntry.this.doHandle(md);
		}
		return true;
	}

	protected void doHandle(MsgWrapper md) {
		try {
			this.handlers.handle(md);
		} catch (Throwable t) {
			LOG.error("handler exception for msg:" + md, t);
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
