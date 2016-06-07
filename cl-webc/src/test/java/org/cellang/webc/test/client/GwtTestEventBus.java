package org.cellang.webc.test.client;

import org.cellang.clwt.core.client.event.AfterClientStartEvent;
import org.cellang.clwt.core.client.event.Event.EventHandlerI;
import org.cellang.clwt.core.client.logger.WebLogger;
import org.cellang.clwt.core.client.logger.WebLoggerFactory;

public class GwtTestEventBus extends AbstractGwtTestBase {
	//WebLogger LOG = WebLoggerFactory.getLogger(GwtTestEventBus.class);

	public void test() {
		this.finishing.add("local.called");
		this.finishing.add("global.called");
		this.client.addHandler(AfterClientStartEvent.TYPE, new EventHandlerI<AfterClientStartEvent>() {

			@Override
			public void handle(AfterClientStartEvent t) {
				GwtTestEventBus.this.handlerInClientCalled();
			}
		});
		this.client.getEventBus(true).addHandler(new EventHandlerI<AfterClientStartEvent>() {

			@Override
			public void handle(AfterClientStartEvent t) {
				GwtTestEventBus.this.handlerInEventBusCalled();
			}
		});
		new AfterClientStartEvent(this.client).dispatch();
		this.delayTestFinish(10 * 1000);
	}

	private void handlerInClientCalled() {
		//LOG.info("local.called");//
		this.tryFinish("local.called");
	}

	private void handlerInEventBusCalled() {
		//LOG.info("global.called");

		this.tryFinish("global.called");//
	}
}
