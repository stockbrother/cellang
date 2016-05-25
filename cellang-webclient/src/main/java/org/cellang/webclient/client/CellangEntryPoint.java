package org.cellang.webclient.client;

import org.cellang.webframework.client.ClFrameworkWebModule;
import org.cellang.webframework.client.ClientLoader;
import org.cellang.webframework.client.Container;
import org.cellang.webframework.client.WebClient;
import org.cellang.webframework.client.event.Event;
import org.cellang.webframework.client.event.Event.EventHandlerI;
import org.cellang.webframework.client.lang.WebModule;
import org.cellang.webframework.client.lang.WebModuleFactory;
import org.cellang.webframework.client.widget.WebWidget;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;

public class CellangEntryPoint implements EntryPoint {

	Container container;
	WebClient client;

	@Override
	public void onModuleLoad() {
		System.out.println("onModuleLoad");
		
		WebModule[] spis = new WebModule[] { (ClFrameworkWebModule) GWT.create(ClFrameworkWebModule.class) };
		
		WebModuleFactory sf = ((ClientLoader) GWT.create(ClientLoader.class)).getOrLoadClient(spis,
				new EventHandlerI<Event>() {

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
