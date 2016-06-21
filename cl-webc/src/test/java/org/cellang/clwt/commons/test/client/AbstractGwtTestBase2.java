package org.cellang.clwt.commons.test.client;

import org.cellang.clwt.core.client.ClientLanucher;
import org.cellang.clwt.core.client.ClientObject;
import org.cellang.clwt.core.client.Container;
import org.cellang.clwt.core.client.event.Event;
import org.cellang.clwt.core.client.event.Event.EventHandlerI;
import org.cellang.clwt.core.client.lang.Plugin;
import org.cellang.clwt.core.client.lang.Plugins;

import com.google.gwt.core.client.GWT;

public abstract class AbstractGwtTestBase2 extends AbstractGwtTestBase {

	protected ClientObject client;
	protected Container container;

	@Override
	public String getModuleName() {
		return "org.cellang.clwt.commons.test.Module";
	}

	protected void loadClient() {

		Plugin[] spis = this.getPlugins();

		Plugins sf = ((ClientLanucher) GWT.create(ClientLanucher.class)).getOrLoadClient(spis,
				new EventHandlerI<Event>() {

					@Override
					public void handle(Event e) {
						AbstractGwtTestBase2.this.onEvent(e);
					}
				});

		this.container = sf.getContainer();
		client = this.container.get(ClientObject.class, true);
	}

	protected abstract Plugin[] getPlugins();
	protected abstract void onEvent(Event e);
}
