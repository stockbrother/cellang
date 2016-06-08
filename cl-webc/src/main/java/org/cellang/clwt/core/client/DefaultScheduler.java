/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 21, 2012
 */
package org.cellang.clwt.core.client;

import java.util.HashMap;
import java.util.Map;

import org.cellang.clwt.core.client.event.ScheduleEvent;
import org.cellang.clwt.core.client.event.Event.EventHandlerI;
import org.cellang.clwt.core.client.lang.AbstractWebObject;
import org.cellang.clwt.core.client.lang.Handler;
import org.cellang.clwt.core.client.lang.Path;
import org.cellang.clwt.core.client.logger.WebLogger;
import org.cellang.clwt.core.client.logger.WebLoggerFactory;

import com.google.gwt.core.client.Scheduler.RepeatingCommand;
import com.google.gwt.user.client.Timer;

/**
 * @author wu
 * 
 */
public class DefaultScheduler extends AbstractWebObject implements Scheduler, ContainerAware {

	private static final WebLogger LOG = WebLoggerFactory.getLogger(DefaultScheduler.class);

	/**
	 * @param c
	 */
	public DefaultScheduler(Container c) {
		super(c);
	}

	private static class ScheduleTask implements RepeatingCommand {

		public DefaultScheduler scheduler;

		public String name;

		public int intervalMS;

		public Path path;

		public ScheduleTask(DefaultScheduler sch, String name, int ims) {
			this.scheduler = sch;
			this.name = name;
			this.intervalMS = ims;
			this.path = ScheduleEvent.TYPE.getAsPath().getSubPath(this.name);
		}

		/*
		 * Jan 11, 2013
		 */
		@Override
		public boolean execute() {
			//
			// LOG.debug("execute task:" + this.name);
			new ScheduleEvent(this.scheduler, path).dispatch();
			return true;
		}

	}

	private Container container;

	private Map<String, ScheduleTask> taskMap = new HashMap<String, ScheduleTask>();

	@Override
	public void scheduleRepeat(String name, int intervalMS) {
		LOG.info("scheduleRepeat,name:" + name + ",intervalMS:" + intervalMS);
		ScheduleTask rt = this.getTask(name, false);
		if (rt == null) {
			ScheduleTask rti = new ScheduleTask(this, name, intervalMS);
			com.google.gwt.core.client.Scheduler.get().scheduleFixedPeriod(rti, rti.intervalMS);
		} else {
			throw new UiException("schedule task already exist:" + rt);
		}
	}

	/*
	 * Oct 21, 2012
	 */
	@Override
	public void scheduleRepeat(String name, int intervalMS, EventHandlerI<ScheduleEvent> eh) {
		this.scheduleRepeat(name, intervalMS);
		Path p = ScheduleEvent.TYPE.getAsPath().getSubPath(name);
		this.addHandler(p, eh);

	}

	public ScheduleTask getTask(String name, boolean force) {
		ScheduleTask rt = this.taskMap.get(name);

		if (rt == null && force) {
			throw new UiException("no task by name:" + name);
		}
		return rt;
	}

	/*
	 * Oct 21, 2012
	 */
	@Override
	public void setContainer(Container c) {
		//
		this.container = c;
	}

	@Override
	public Container getContainer() {
		return this.container;
	}

	/*
	 * Jan 11, 2013
	 */
	@Override
	public void cancel(String name) {
		throw new UiException("TODO");
	}

	/*
	 * Mar 30, 2013
	 */
	@Override
	public void scheduleTimer(int delay, final Handler<Object> eh) {
		//
		new Timer() {

			@Override
			public void run() {
				//
				eh.handle(null);
			}
		}.schedule(delay);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * scheduler.SchedulerI#scheduleDelay(com.fs
	 * .uicore.api.gwt.client.HandlerI)
	 */
	@Override
	public void scheduleDelay(final Handler<Object> eh) {
		new Timer() {

			@Override
			public void run() {
				//
				eh.handle(null);
			}
		}.schedule(0);

	}
}
