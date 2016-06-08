package org.cellang.webc.test.client;

import java.util.HashSet;
import java.util.Set;

import org.cellang.clwt.core.client.ClientLoader;
import org.cellang.clwt.core.client.Container;
import org.cellang.clwt.core.client.WebClient;
import org.cellang.clwt.core.client.WebCorePlugin;
import org.cellang.clwt.core.client.event.Event;
import org.cellang.clwt.core.client.event.Event.EventHandlerI;
import org.cellang.clwt.core.client.lang.Plugin;
import org.cellang.clwt.core.client.lang.Plugins;
import org.cellang.webc.main.client.CellangClientPlugin;

import com.google.gwt.core.client.GWT;
import com.google.gwt.junit.client.GWTTestCase;

public abstract class AbstractGwtTestBase2 extends AbstractGwtTestBase {

	protected WebClient client;
	protected Container container;

	@Override
	protected void gwtSetUp() throws Exception {
		super.gwtSetUp();

		Plugin[] spis = new Plugin[] { //
				(WebCorePlugin) GWT.create(WebCorePlugin.class), //
				(CellangClientPlugin) GWT.create(CellangClientPlugin.class) //
		};

		Plugins sf = ((ClientLoader) GWT.create(ClientLoader.class)).getOrLoadClient(spis, new EventHandlerI<Event>() {

			@Override
			public void handle(Event e) {
				AbstractGwtTestBase2.this.onEvent(e);
			}
		});

		this.container = sf.getContainer();
		client = this.container.get(WebClient.class, true);
	}

	protected void onEvent(Event e) {

	}

	@Override
	protected void gwtTearDown() throws Exception {

		super.gwtTearDown();
	}

}
