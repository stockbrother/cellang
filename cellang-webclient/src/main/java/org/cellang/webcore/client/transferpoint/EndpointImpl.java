/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 20, 2012
 */
package org.cellang.webcore.client.transferpoint;

import java.util.HashMap;
import java.util.Map;

import org.cellang.webcore.client.Container;
import org.cellang.webcore.client.event.ClientClosingEvent;
import org.cellang.webcore.client.lang.Address;
import org.cellang.webcore.client.lang.Handler;
import org.cellang.webcore.client.message.MessageDispatcherI;
import org.cellang.webcore.client.transferpoint.ws.WebSocketProtocol;

import com.google.gwt.user.client.Window;

/**
 * @author wu
 * 
 */
public class EndpointImpl extends AbstractTransferPoint {

	public static interface UnderlyingProtocol {
		public TransferProvider createGomet(Address uri, boolean force);
	}

	private TransferProvider socket;

	private Map<String, UnderlyingProtocol> protocols;

	private long openTimeout = 3000;//

	/**
	 * @param md
	 */
	public EndpointImpl(Container c, Address uri, MessageDispatcherI md) {
		super(c, uri, md, new MessageCacheImpl(c));
		this.protocols = new HashMap<String, UnderlyingProtocol>();
		this.protocols.put("ws", new WebSocketProtocol());
		this.protocols.put("wss", new WebSocketProtocol());

		this.protocols.put("http", new AjaxProtocol(c));
		this.protocols.put("https", new AjaxProtocol(c));

	}

	/*
	 * Dec 20, 2012
	 */
	@Override
	protected void doAttach() {
		super.doAttach();

	}

	/**
	 * Apr 4, 2013
	 */
	protected void onClientClosing(ClientClosingEvent t) {
		this.close();
	}

	@Override
	public void close() {
		this.socket.close();
	}

	@Override
	public void open() {
		super.open();

		String proS = uri.getProtocol();

		UnderlyingProtocol pro = this.protocols.get(proS);

		this.socket = pro.createGomet(uri, false);
		this.socket.open(this.openTimeout);
		if (this.socket == null) {
			Window.alert("protocol is not support:" + proS);
		}

		this.socket.addOpenHandler(new Handler<TransferProvider>() {

			@Override
			public void handle(TransferProvider t) {
				//
				EndpointImpl.this.onConnected();
			}
		});
		this.socket.addMessageHandler(new Handler<String>() {

			@Override
			public void handle(String t) {
				//
				EndpointImpl.this.onMessage(t);

			}
		});
		this.socket.addCloseHandler(new Handler<String>() {

			@Override
			public void handle(String t) {
				//
				EndpointImpl.this.onClosed(t, "");
			}
		});
		this.socket.addErrorHandler(new Handler<String>() {

			@Override
			public void handle(String t) {
				//
				EndpointImpl.this.onError(t);
			}
		});

	}

	@Override
	protected boolean isNativeSocketOpen() {
		if (this.socket == null) {
			return false;
		}

		boolean rs = this.socket.isOpen();
		return rs;

	}

	@Override
	protected void doSendMessage(String jsS, Handler<String> failureHanlder) {
		this.socket.send(jsS, failureHanlder);
	}

	@Override
	public void destroy() {
		this.parent(null);
		this.eventDispatcher.cleanAllHanlders();
	}

}
