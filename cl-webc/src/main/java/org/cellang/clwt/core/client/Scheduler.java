/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 21, 2012
 */
package org.cellang.clwt.core.client;

import org.cellang.clwt.core.client.event.ScheduleEvent;
import org.cellang.clwt.core.client.event.Event.EventHandlerI;
import org.cellang.clwt.core.client.lang.Handler;
import org.cellang.clwt.core.client.lang.WebObject;

/**
 * @author wu
 * 
 */
public interface Scheduler extends WebObject {
	public void scheduleRepeat(String name, int intervelMS);
			
	public void scheduleRepeat(String name, int intervelMS, EventHandlerI<ScheduleEvent> eh);
	
	public void scheduleTimer(int timeout, Handler<Object> eh);
	
	public void scheduleDelay(Handler<Object> eh);

	public void cancel(String name);
}
