/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 11, 2013
 */
package org.cellang.clwt.commons.client;

import org.cellang.clwt.core.client.Scheduler;
import org.cellang.clwt.core.client.WebClient;
import org.cellang.clwt.core.client.event.EndpointMessageEvent;
import org.cellang.clwt.core.client.event.Event.EventHandlerI;
import org.cellang.clwt.core.client.event.ScheduleEvent;
import org.cellang.clwt.core.client.logger.WebLogger;
import org.cellang.clwt.core.client.logger.WebLoggerFactory;
import org.cellang.clwt.core.client.message.MsgWrapper;
import org.cellang.clwt.core.client.transfer.Endpoint;

/**
 * @author wu
 * @deprecated move to uicore.
 */
public class EndpointKeeper {

	private static final WebLogger LOG = WebLoggerFactory.getLogger(EndpointKeeper.class);

	protected Endpoint endpoint;

	private WebClient client;

	private String taskName = "endpoint-keeper";

	public EndpointKeeper(WebClient c) {
		this.endpoint = c.getEndpoint(true);
		this.client = c;

	}

	public void start() {
		this.endpoint.addHandler(EndpointMessageEvent.TYPE, new EventHandlerI<EndpointMessageEvent>() {

			@Override
			public void handle(EndpointMessageEvent t) {
				EndpointKeeper.this.onMessage(t);
			}
		});

		int hbI = this.client.getParameterAsInt(UiCommonsConstants.RK_COMET_HEARTBEATINTERVAL, -1);
		if (hbI < 5 * 1000) {// must longer than 5 second
			LOG.error("parameter of interval for heart beat too short:"+hbI);
			return;
		}
		Scheduler s = this.endpoint.getContainer().get(Scheduler.class, true);

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
	protected void onMessage(EndpointMessageEvent t) {
		// TODO /ping/success

	}

}
