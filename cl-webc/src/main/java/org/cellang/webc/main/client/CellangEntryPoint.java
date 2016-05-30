package org.cellang.webc.main.client;

import org.cellang.clwt.commons.client.frwk.impl.FrwkControlImpl;
import org.cellang.clwt.commons.client.mvc.ControlManager;
import org.cellang.clwt.commons.client.mvc.impl.ControlManagerImpl;
import org.cellang.clwt.core.client.ClientLoader;
import org.cellang.clwt.core.client.Container;
import org.cellang.clwt.core.client.WebClient;
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
	WebClient client;

	@Override
	public void onModuleLoad() {
		System.out.println("onModuleLoad");

		Plugin[] spis = new Plugin[] { //
				(WebCorePlugin) GWT.create(WebCorePlugin.class), //
				(CellangClientPlugin) GWT.create(CellangClientPlugin.class) //
				};

		Plugins sf = ((ClientLoader) GWT.create(ClientLoader.class)).getOrLoadClient(spis, new EventHandlerI<Event>() {

			@Override
			public void handle(Event e) {
				// TODO
			}
		});

		this.container = sf.getContainer();
		client = this.container.get(WebClient.class, true);

		client.start();
		WebWidget root = client.getRoot();
		/****/
		System.out.println("end of onModuleLoad");

	}

}
