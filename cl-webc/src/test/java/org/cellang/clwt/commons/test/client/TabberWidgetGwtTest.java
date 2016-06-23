package org.cellang.clwt.commons.test.client;

import org.cellang.clwt.commons.client.CommonsPlugin;
import org.cellang.clwt.commons.client.widget.ButtonI;
import org.cellang.clwt.commons.client.widget.TabWI;
import org.cellang.clwt.commons.client.widget.TabberWI;
import org.cellang.clwt.core.client.UiException;
import org.cellang.clwt.core.client.WebCorePlugin;
import org.cellang.clwt.core.client.event.Event;
import org.cellang.clwt.core.client.lang.Path;
import org.cellang.clwt.core.client.lang.Plugin;
import org.cellang.clwt.core.client.logger.WebLogger;
import org.cellang.clwt.core.client.logger.WebLoggerFactory;
import org.cellang.clwt.core.client.transfer.AbstractLogicalChannel;
import org.cellang.clwt.core.client.widget.WebWidgetFactory;

import com.google.gwt.core.client.GWT;

public class TabberWidgetGwtTest extends AbstractGwtTestBase2 {

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
		WebWidgetFactory wwf = this.container.getWidgetFactory(true);
		TabberWI tabber = wwf.create(TabberWI.class);
		ButtonI button1 = wwf.create(ButtonI.class);

		Path path = Path.valueOf("button");
		tabber.addTab(path, button1);

		ButtonI button2 = wwf.create(ButtonI.class);
		UiException exp = null;
		try {
			tabber.addTab(path, button2);
		} catch (UiException e) {
			exp = e;
		}
		assertNotNull("exception not found!", exp);

		tabber.remove(path);
		TabWI tab = tabber.getTab(path, false);
		assertNull("tab remove failure.", tab);
		tabber.addTab(path, button2);
		//this.finishTest();
		//this.delayTestFinish(10 * 1000);
	}

	@Override
	protected void onEvent(Event e) {

	}

}
