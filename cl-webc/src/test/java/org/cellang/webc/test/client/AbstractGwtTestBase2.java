package org.cellang.webc.test.client;

import org.cellang.clwt.commons.client.CommonsPlugin;
import org.cellang.clwt.core.client.ClientLanucher;
import org.cellang.clwt.core.client.ClientObject;
import org.cellang.clwt.core.client.Container;
import org.cellang.clwt.core.client.WebCorePlugin;
import org.cellang.clwt.core.client.event.Event;
import org.cellang.clwt.core.client.event.Event.EventHandlerI;
import org.cellang.clwt.core.client.lang.Plugin;
import org.cellang.clwt.core.client.lang.Plugins;
import org.cellang.webc.main.client.CellangClientPlugin;

import com.google.gwt.core.client.GWT;

public abstract class AbstractGwtTestBase2 extends AbstractGwtTestBase {

	protected ClientObject client;
	protected Container container;

	@Override
	protected void gwtSetUp() throws Exception {
		super.gwtSetUp();

		Plugin[] spis = new Plugin[] { //
				(WebCorePlugin) GWT.create(WebCorePlugin.class), //
				(CommonsPlugin) GWT.create(CommonsPlugin.class), //
				(CellangClientPlugin) GWT.create(CellangClientPlugin.class) //
		};

		Plugins sf = ((ClientLanucher) GWT.create(ClientLanucher.class)).getOrLoadClient(spis, new EventHandlerI<Event>() {

			@Override
			public void handle(Event e) {
				AbstractGwtTestBase2.this.onEvent(e);
			}
		});

		this.container = sf.getContainer();
		client = this.container.get(ClientObject.class, true);
	}

	protected void onEvent(Event e) {

	}

	@Override
	protected void gwtTearDown() throws Exception {

		super.gwtTearDown();
	}

}
