package org.cellang.clwt.commons.test.client;

import org.cellang.clwt.commons.client.CommonsPlugin;
import org.cellang.clwt.commons.client.frwk.FrwkControlI;
import org.cellang.clwt.commons.client.frwk.HeaderItemEvent;
import org.cellang.clwt.commons.client.frwk.impl.FrwkControlImpl;
import org.cellang.clwt.commons.client.mvc.ControlManager;
import org.cellang.clwt.commons.client.mvc.impl.ControlManagerImpl;
import org.cellang.clwt.core.client.WebCorePlugin;
import org.cellang.clwt.core.client.event.ClientStartingEvent;
import org.cellang.clwt.core.client.event.Event;
import org.cellang.clwt.core.client.event.Event.EventHandlerI;
import org.cellang.clwt.core.client.event.LogicalChannelMessageEvent;
import org.cellang.clwt.core.client.lang.Path;
import org.cellang.clwt.core.client.lang.Plugin;
import org.cellang.clwt.core.client.logger.WebLogger;
import org.cellang.clwt.core.client.logger.WebLoggerFactory;
import org.cellang.clwt.core.client.transfer.AbstractLogicalChannel;

import com.google.gwt.core.client.GWT;

public class HeaderItemGwtTest extends AbstractGwtTestBase2 {

	@Override
	protected Plugin[] getPlugins() {
		return new Plugin[] { //
				(WebCorePlugin) GWT.create(WebCorePlugin.class), //
				(CommonsPlugin) GWT.create(CommonsPlugin.class) //
		};
	}

	public void testHeaderItem() {
		WebLoggerFactory.configure(WebLogger.LEVEL_DEBUG);//
		WebLoggerFactory.configure(AbstractLogicalChannel.class, WebLogger.LEVEL_TRACE);
		// WebLoggerFactory.configure(AbstractWebObject.class,WebLogger.LEVEL_TRACE);
		// WebLoggerFactory.configure(DispatcherImpl.class,WebLogger.LEVEL_TRACE);
		// WebLoggerFactory.configure(CollectionHandler.class,WebLogger.LEVEL_TRACE);
		// WebLoggerFactory.configure(HandlerEntry.class,WebLogger.LEVEL_TRACE);
		this.loadClient();

		ControlManager manager = new ControlManagerImpl(this.container);
		manager.parent(client);
		manager.child(new FrwkControlImpl(this.container, "frwk"));

		this.client.start();
		System.out.println("testHeaderItem");
		this.finishing.add("starting");
		this.finishing.add("handled");
		//

		this.delayTestFinish(10 * 1000);
	}

	@Override
	protected void onEvent(Event e) {
		System.out.println("TestBase.onEvent(),e:" + e);

		if (e instanceof ClientStartingEvent) {
			ClientStartingEvent afe = (ClientStartingEvent) e;

			this.onClientStarting(afe);
		} else if (e instanceof LogicalChannelMessageEvent) {

		}

	}

	protected ControlManager getControlManager() {
		return this.client.getChild(ControlManager.class, true);
	}

	private void onClientStarting(ClientStartingEvent afe) {
		this.tryFinish("starting");
		
		FrwkControlI fc = this.getControlManager().getControl(FrwkControlI.class, true);
		fc.open();
		Path ITEM = Path.valueOf("abc.def");
		
		fc.addHeaderItem(ITEM, new EventHandlerI<HeaderItemEvent>() {

			@Override
			public void handle(HeaderItemEvent t) {
				HeaderItemGwtTest.this.onHeaderItem(t);
			}
		});
		fc.getHeaderView()._clickItem(ITEM);
	}

	protected void onHeaderItem(HeaderItemEvent t) {
		this.tryFinish("handled");//
	}

}
