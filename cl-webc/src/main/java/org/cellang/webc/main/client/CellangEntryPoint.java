package org.cellang.webc.main.client;

import org.cellang.clwt.commons.client.CommonsPlugin;
import org.cellang.clwt.core.client.ClientLanucher;
import org.cellang.clwt.core.client.Container;
import org.cellang.clwt.core.client.ClientObject;
import org.cellang.clwt.core.client.WebCorePlugin;
import org.cellang.clwt.core.client.event.Event;
import org.cellang.clwt.core.client.event.Event.EventHandlerI;
import org.cellang.clwt.core.client.lang.Plugin;
import org.cellang.clwt.core.client.lang.Plugins;
import org.cellang.clwt.core.client.widget.WebWidget;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;

public class CellangEntryPoint implements EntryPoint {

	Container container;
	ClientObject client;

	@Override
	public void onModuleLoad() {
		System.out.println("onModuleLoad");

		Plugin[] spis = new Plugin[] { //
				(WebCorePlugin) GWT.create(WebCorePlugin.class), //
				(CommonsPlugin) GWT.create(CommonsPlugin.class), //
				(CellangClientPlugin) GWT.create(CellangClientPlugin.class) //
				};

		Plugins sf = ((ClientLanucher) GWT.create(ClientLanucher.class)).getOrLoadClient(spis, new EventHandlerI<Event>() {

			@Override
			public void handle(Event e) {
				// TODO
			}
		});

		this.container = sf.getContainer();
		client = this.container.get(ClientObject.class, true);

		client.start();
		WebWidget root = client.getRoot();
		/****/
		System.out.println("end of onModuleLoad");

	}

}
