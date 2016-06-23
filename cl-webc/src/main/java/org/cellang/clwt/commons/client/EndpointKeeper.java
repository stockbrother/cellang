/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 11, 2013
 */
package org.cellang.clwt.commons.client;

import org.cellang.clwt.core.client.Scheduler;
import org.cellang.clwt.core.client.ClientObject;
import org.cellang.clwt.core.client.event.LogicalChannelMessageEvent;
import org.cellang.clwt.core.client.event.Event.EventHandlerI;
import org.cellang.clwt.core.client.event.ScheduleEvent;
import org.cellang.clwt.core.client.logger.WebLogger;
import org.cellang.clwt.core.client.logger.WebLoggerFactory;
import org.cellang.clwt.core.client.message.MsgWrapper;
import org.cellang.clwt.core.client.transfer.LogicalChannel;

/**
 * @author wu
 * @deprecated move to uicore.
 */
public class EndpointKeeper {

	private static final WebLogger LOG = WebLoggerFactory.getLogger(EndpointKeeper.class);

	protected LogicalChannel endpoint;

	private ClientObject client;

	private String taskName = "endpoint-keeper";

	public EndpointKeeper(ClientObject c) {
		this.endpoint = c.getLogicalChannel(true);
		this.client = c;

	}

	public void start() {
		this.endpoint.addHandler(LogicalChannelMessageEvent.TYPE, new EventHandlerI<LogicalChannelMessageEvent>() {

			@Override
			public void handle(LogicalChannelMessageEvent t) {
				EndpointKeeper.this.onMessage(t);
			}
		});

		int hbI = this.client.getParameterAsInt(UiCommonsConstants.RK_COMET_HEARTBEATINTERVAL, -1);
		if (hbI < 5 * 1000) {// must longer than 5 second
			LOG.error("parameter of interval for heart beat too short:" + hbI);
			return;
		}
		Scheduler s = this.endpoint.getContainer().getScheduler(true);

		s.scheduleRepeat(taskName, hbI, new EventHandlerI<ScheduleEvent>() {

			@Override
			public void handle(ScheduleEvent t) {
				EndpointKeeper.this.onScheduleEvent(t);
			}
		});// 30S to send ping

	}

	/**
	 * Jan 11, 2013
	 */
	protected void onScheduleEvent(ScheduleEvent t) {
		LOG.info("sending ping request for keeping the endpoint.");
		MsgWrapper req = new MsgWrapper("/ping/ping");
		req.setPayload("text", "keeper");
		this.endpoint.sendMessage(req);
	}

	/**
	 * Jan 11, 2013
	 */
	protected void onMessage(LogicalChannelMessageEvent t) {
		// TODO /ping/success

	}

}
