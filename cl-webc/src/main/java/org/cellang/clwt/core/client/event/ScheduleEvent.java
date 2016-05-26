/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 21, 2012
 */
package org.cellang.clwt.core.client.event;

import org.cellang.clwt.core.client.Scheduler;
import org.cellang.clwt.core.client.lang.Path;

/**
 * @author wu
 * 
 */
public class ScheduleEvent extends Event {

	public static final Type<ScheduleEvent> TYPE = new Type<ScheduleEvent>("schedule");

	/**
	 * @param type
	 */
	public ScheduleEvent(Scheduler src, Path path) {
		super(TYPE, src, path);
	}

	public String getTask() {
		return this.getPath().getName();
	}

}
