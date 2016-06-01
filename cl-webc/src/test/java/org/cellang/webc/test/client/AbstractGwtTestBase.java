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

public abstract class AbstractGwtTestBase extends GWTTestCase {

	protected Set<String> finishing;
	protected WebClient client;
	protected Container container;

	@Override
	public String getModuleName() {
		return "org.cellang.webc.test.Module";
	}

	@Override
	protected void gwtSetUp() throws Exception {
		this.finishing = new HashSet<String>();

		Plugin[] spis = new Plugin[] { //
				(WebCorePlugin) GWT.create(WebCorePlugin.class), //
				(CellangClientPlugin) GWT.create(CellangClientPlugin.class) //
		};

		Plugins sf = ((ClientLoader) GWT.create(ClientLoader.class)).getOrLoadClient(spis, new EventHandlerI<Event>() {

			@Override
			public void handle(Event e) {
				AbstractGwtTestBase.this.onEvent(e);
			}
		});

		this.container = sf.getContainer();
		client = this.container.get(WebClient.class, true);
	}
	
	public void tryFinish(String finish) {
		this.finishing.remove(finish);
		System.out.println("finish:" + finish + ",waiting:" + this.finishing);
		if (this.finishing.isEmpty()) {
			this.finishTest();
		}
	}
	protected void onEvent(Event e) {

	}

	@Override
	protected void gwtTearDown() throws Exception {
	}

}
