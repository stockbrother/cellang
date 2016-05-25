/**
 * All right is from Author of the file,to be explained in comming days.
 * Sep 22, 2012
 */
package org.cellang.webframework.client.impl;

import java.util.HashMap;
import java.util.Map;

import org.cellang.webframework.client.ClientLoader;
import org.cellang.webframework.client.Console;
import org.cellang.webframework.client.Container;
import org.cellang.webframework.client.WebClient;
import org.cellang.webframework.client.core.ElementWrapper;
import org.cellang.webframework.client.event.AfterClientStartEvent;
import org.cellang.webframework.client.event.ClientConnectLostEvent;
import org.cellang.webframework.client.event.ClientStartFailureEvent;
import org.cellang.webframework.client.event.Event;
import org.cellang.webframework.client.event.EventBus;
import org.cellang.webframework.client.event.Event.EventHandlerI;
import org.cellang.webframework.client.lang.WebModuleFactory;
import org.cellang.webframework.client.lang.WebModule;
import org.cellang.webframework.client.lang.Callback;
import org.cellang.webframework.client.logger.WebLoggerFactory;
import org.cellang.webframework.client.logger.WebLogger;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;
import com.google.gwt.http.client.UrlBuilder;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;

/**
 * @author wu Test support.
 */
public class DefaultClientLoader extends ClientLoader {

	private WebLogger LOG = WebLoggerFactory.getLogger(DefaultClientLoader.class);

	private static Map<String, WebModuleFactory> CACHE = new HashMap<String, WebModuleFactory>();

	protected Element table;

	protected ElementWrapper tbody;

	protected Element element;

	protected int size;

	protected int maxSize = 1000;

	private Callback<Object, Boolean> handler;

	private boolean show;

	private int retry;

	public DefaultClientLoader() {
		GWT.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {

			@Override
			public void onUncaughtException(Throwable e) {
				LOG.error("Uncaught exception is got", e);
			}
		});

		Element root = DefaultRootWidget.getRootElement();

		this.element = DOM.createDiv();
		root.appendChild(this.element);//
		this.element.addClassName("loader");
		this.table = DOM.createTable();
		this.tbody = new ElementWrapper(DOM.createTBody());
		this.table.appendChild(this.tbody.getElement());
		this.element.appendChild(this.table);
		this.handler = new Callback<Object, Boolean>() {

			@Override
			public Boolean execute(Object t) {
				//
				DefaultClientLoader.this.println(t);//
				return null;
			}
		};
		this.show();
	}

	private void println(Object msg) {
		Element tr = DOM.createTR();
		DOM.appendChild(this.tbody.getElement(), tr);

		Element td = DOM.createTD();
		String text = "" + msg;
		td.setInnerText(text);
		DOM.appendChild(tr, td);

		this.size++;
		this.element.setAttribute("scrollTop", "10000px");// Scroll to
															// bottom.
		this.shrink();

	}

	public void shrink() {
		// shrink
		while (true) {
			if (this.size <= this.maxSize) {
				break;
			}
			com.google.gwt.dom.client.Element ele = this.tbody.getElement().getFirstChildElement();
			if (ele == null) {
				break;
			}
			ele.removeFromParent();
			this.size--;
		}

	}

	@Override
	public WebModuleFactory getOrLoadClient(WebModule[] spis, EventHandlerI<Event> l) {

		String key = "";
		for (int i = 0; i < spis.length; i++) {
			key += spis[i] + ",";
		}

		WebModuleFactory rt = CACHE.get(key);
		if (rt != null) {
			return rt;
		}
		rt = loadClient(spis, l);
		CACHE.put(key, rt);//
		return rt;
	}

	@Override
	public WebModuleFactory loadClient(WebModule[] spis, EventHandlerI<Event> l) {

		WebModuleFactory factory = WebModuleFactory.create();

		final Container container = factory.getContainer();
		EventBus eb = container.getEventBus();
		if (l != null) {
			eb.addHandler(l);
		}

		eb.addHandler(AfterClientStartEvent.TYPE, new EventHandlerI<AfterClientStartEvent>() {

			@Override
			public void handle(AfterClientStartEvent t) {
				DefaultClientLoader.this.afterClientStart(container);
			}
		});
		eb.addHandler(ClientStartFailureEvent.TYPE, new EventHandlerI<ClientStartFailureEvent>() {

			@Override
			public void handle(ClientStartFailureEvent t) {
				//
				DefaultClientLoader.this.onClientStartFailureEvent(container, t);
			}
		});
		eb.addHandler(ClientConnectLostEvent.TYPE, new EventHandlerI<ClientConnectLostEvent>() {

			@Override
			public void handle(ClientConnectLostEvent t) {
				//
				DefaultClientLoader.this.onClientConnectLostEvent(container, t);
			}
		});

		factory.active(spis);

		WebClient client = container.get(WebClient.class, true);

		client.attach();// NOTE

		return factory;
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
		this.show();
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
		WebClient client = container.get(WebClient.class, true);
		this.hide();
	}

	private void show() {
		if (this.show) {
			return;
		}
		Console.getInstance().addMessageCallback(this.handler);//
		// hide the loader view.
		this.element.removeClassName("invisible");
		this.show = true;

	}

	private void hide() {
		if (!this.show) {
			return;
		}
		// not listen more message from console
		Console.getInstance().removeMessageCallback(this.handler);//
		// hide the loader view.
		this.element.addClassName("invisible");// hiden
		this.show = false;
	}
}
