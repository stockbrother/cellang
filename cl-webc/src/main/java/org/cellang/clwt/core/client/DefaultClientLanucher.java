/**
 * All right is from Author of the file,to be explained in comming days.
 * Sep 22, 2012
 */
package org.cellang.clwt.core.client;

import java.util.HashMap;
import java.util.Map;

import org.cellang.clwt.core.client.event.ClientConnectLostEvent;
import org.cellang.clwt.core.client.event.ClientStartFailureEvent;
import org.cellang.clwt.core.client.event.ClientStartedEvent;
import org.cellang.clwt.core.client.event.Event;
import org.cellang.clwt.core.client.event.Event.EventHandlerI;
import org.cellang.clwt.core.client.event.EventBus;
import org.cellang.clwt.core.client.lang.Plugin;
import org.cellang.clwt.core.client.lang.Plugins;
import org.cellang.clwt.core.client.logger.WebLogger;
import org.cellang.clwt.core.client.logger.WebLoggerFactory;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;
import com.google.gwt.http.client.UrlBuilder;
import com.google.gwt.user.client.Window;

/**
 * @author wu Test support.
 */
public class DefaultClientLanucher extends ClientLanucher {

	private WebLogger LOG = WebLoggerFactory.getLogger(DefaultClientLanucher.class);

	private static Map<String, Plugins> CACHE = new HashMap<String, Plugins>();

	protected int size;

	protected int maxSize = 1000;

	public DefaultClientLanucher() {
		GWT.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {

			@Override
			public void onUncaughtException(Throwable e) {
				e.printStackTrace();
				LOG.error("Uncaught exception is got", e);
			}
		});

	}


	@Override
	public Plugins getOrLoadClient(Plugin[] spis, EventHandlerI<Event> l) {

		String key = "";
		for (int i = 0; i < spis.length; i++) {
			key += spis[i] + ",";
		}

		Plugins rt = CACHE.get(key);
		if (rt != null) {
			return rt;
		}
		rt = loadClient(spis, l);
		CACHE.put(key, rt);//
		return rt;
	}

	@Override
	public Plugins loadClient(Plugin[] spis, EventHandlerI<Event> l) {

		if (LOG.isDebugEnabled()) {
			LOG.debug("before loadClient:" + spis);
		}
		 
		try {
			Plugins	factory = Plugins.create();

			final Container container = factory.getContainer();
			EventBus eb = container.getEventBus();
			if (l != null) {
				eb.addHandler(l);
			}

			eb.addHandler(ClientStartedEvent.TYPE, new EventHandlerI<ClientStartedEvent>() {

				@Override
				public void handle(ClientStartedEvent t) {
					DefaultClientLanucher.this.afterClientStart(container);
				}
			});
			eb.addHandler(ClientStartFailureEvent.TYPE, new EventHandlerI<ClientStartFailureEvent>() {

				@Override
				public void handle(ClientStartFailureEvent t) {
					//
					DefaultClientLanucher.this.onClientStartFailureEvent(container, t);
				}
			});
			eb.addHandler(ClientConnectLostEvent.TYPE, new EventHandlerI<ClientConnectLostEvent>() {

				@Override
				public void handle(ClientConnectLostEvent t) {
					//
					DefaultClientLanucher.this.onClientConnectLostEvent(container, t);
				}
			});

			factory.active(spis);

			ClientObject client = container.getClient(true);

			return factory;
		} catch (Throwable t) {
			LOG.error("failure to load client,will return null", t);
		}

		if (LOG.isDebugEnabled()) {
			LOG.debug("after loadClient:" + spis);
		}

		return null;
	}

	/**
	 * Apr 21, 2013
	 */
	protected void onClientConnectLostEvent(Container container, ClientConnectLostEvent t) {
		this.retry("Connection to server is lost, retry?");
	}

	protected void onClientStartFailureEvent(Container container, ClientStartFailureEvent t) {
		this.retry("Client starting failed, retry?");
	}

	protected void retry(String msg) {
		// disconnected to server,
		
		boolean rec = Window.confirm(msg);
		if (rec) {

			UrlBuilder urlB = Window.Location.createUrlBuilder();
			String uri = urlB.buildString();
			Window.Location.assign(uri);
		}
	}

	/**
	 * Apr 21, 2013
	 */
	protected void afterClientStart(Container container) {
		//
		LOG.debug(
				"afterClientStart,to hide the client this window is stoped to listening any new message from console!!!!!!!");//
		ClientObject client = container.getClient(true);
		// this.hide();
	}

	
}
