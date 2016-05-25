package org.cellang.webclient.client;

import org.cellang.webcore.client.ClWebCoreImplementer;
import org.cellang.webcore.client.ClientLoader;
import org.cellang.webcore.client.Container;
import org.cellang.webcore.client.WebClient;
import org.cellang.webcore.client.event.Event;
import org.cellang.webcore.client.event.Event.EventHandlerI;
import org.cellang.webcore.client.lang.Implementer;
import org.cellang.webcore.client.lang.ImplementerFactory;
import org.cellang.webcore.client.widget.WebWidget;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;

public class CellangEntryPoint implements EntryPoint {

	Container container;
	WebClient client;

	@Override
	public void onModuleLoad() {
		System.out.println("onModuleLoad");
		
		Implementer[] spis = new Implementer[] { (ClWebCoreImplementer) GWT.create(ClWebCoreImplementer.class) };
		
		ImplementerFactory sf = ((ClientLoader) GWT.create(ClientLoader.class)).getOrLoadClient(spis,
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
