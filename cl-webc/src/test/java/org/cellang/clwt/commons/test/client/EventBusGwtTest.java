package org.cellang.clwt.commons.test.client;

import org.cellang.clwt.core.client.Container;
import org.cellang.clwt.core.client.DefaultContainer;
import org.cellang.clwt.core.client.event.Event;
import org.cellang.clwt.core.client.event.Event.EventHandlerI;
import org.cellang.clwt.core.client.event.EventBus;
import org.cellang.clwt.core.client.event.EventBusImpl;
import org.cellang.clwt.core.client.lang.AbstractWebObject;
import org.cellang.clwt.core.client.lang.Plugin;

public class EventBusGwtTest extends AbstractGwtTestBase2 {
	//WebLogger LOG = WebLoggerFactory.getLogger(GwtTestEventBus.class);
	private static class MockWebObject extends AbstractWebObject{

		public MockWebObject(Container c) {
			super(c);
		}		
	}
	
	public static class MockEvent extends Event {
		public static Type<MockEvent> TYPE = new Type<MockEvent>("mock");

		/** */
		public MockEvent(MockWebObject client) {
			super(TYPE, client);
		}

		public MockWebObject getClient() {
			return (MockWebObject) this.source;
		}

	}
	public void test() {
		this.finishing.add("local.called");
		this.finishing.add("global.called");
		Container c = new DefaultContainer();
		EventBus eb = new EventBusImpl(c);
		MockWebObject wo = new MockWebObject(c);
		EventBus eb2 = wo.getEventBus(false);
		this.assertNotNull(eb2);
		
		
		wo.addHandler(MockEvent.TYPE, new EventHandlerI<MockEvent>() {

			@Override
			public void handle(MockEvent t) {
				EventBusGwtTest.this.handlerInClientCalled();
			}
		});
		wo.getEventBus(true).addHandler(new EventHandlerI<MockEvent>() {

			@Override
			public void handle(MockEvent t) {
				EventBusGwtTest.this.handlerInEventBusCalled();
			}
		});
		new MockEvent(wo).dispatch();
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

	@Override
	protected Plugin[] getPlugins() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void onEvent(Event e) {
		// TODO Auto-generated method stub
		
	}
}
