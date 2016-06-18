/**
 * All right is from Author of the file,to be explained in comming days.
 * Dec 20, 2012
 */
package org.cellang.clwt.core.client.transfer;

import java.util.HashMap;
import java.util.Map;

import org.cellang.clwt.core.client.Container;
import org.cellang.clwt.core.client.DefaultClientObject;
import org.cellang.clwt.core.client.event.ClientClosingEvent;
import org.cellang.clwt.core.client.lang.Address;
import org.cellang.clwt.core.client.lang.DispatcherImpl;
import org.cellang.clwt.core.client.lang.Handler;
import org.cellang.clwt.core.client.lang.Path;
import org.cellang.clwt.core.client.lang.PathBasedDispatcher;
import org.cellang.clwt.core.client.logger.WebLogger;
import org.cellang.clwt.core.client.logger.WebLoggerFactory;
import org.cellang.clwt.core.client.message.MessageHandlerI;
import org.cellang.clwt.core.client.message.MsgWrapper;
import org.cellang.clwt.core.client.transfer.ws.WebSocketProtocol;

import com.google.gwt.user.client.Window;

/**
 * @author wu
 * 
 */
public class DefaultLogicalChannel extends AbstractLogicalChannel {
	private static final WebLogger LOG = WebLoggerFactory.getLogger(DefaultLogicalChannel.class);

	public static interface UnderlyingProtocol {
		public UnderlyingChannel createGomet(Address uri, boolean force);
	}

	private UnderlyingChannel socket;

	private Map<String, UnderlyingProtocol> protocols;

	private long openTimeout = 3000;//


	/**
	 * @param md
	 */
	public DefaultLogicalChannel(Container c, Address uri) {
		super(c, uri, new MessageCacheImpl(c));
		this.protocols = new HashMap<String, UnderlyingProtocol>();
		//this.protocols.put("wskt", new WebSocketProtocol());
		//this.protocols.put("wskts", new WebSocketProtocol());

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
		LOG.info("open endpoint:" + this.name + ",uri:" + this.uri.toString());//
		super.open();

		String proS = uri.getProtocol();

		UnderlyingProtocol pro = this.protocols.get(proS);

		this.socket = pro.createGomet(uri, false);
		this.socket.open(this.openTimeout);
		if (this.socket == null) {
			Window.alert("protocol is not support:" + proS);
		}

		this.socket.addOpenHandler(new Handler<UnderlyingChannel>() {

			@Override
			public void handle(UnderlyingChannel t) {
				//
				DefaultLogicalChannel.this.onConnected();
			}
		});
		this.socket.addMessageHandler(new Handler<String>() {

			@Override
			public void handle(String t) {
				//
				DefaultLogicalChannel.this.onRawMessage(t);

			}
		});
		this.socket.addCloseHandler(new Handler<String>() {

			@Override
			public void handle(String t) {
				//
				DefaultLogicalChannel.this.onClosed(t, "");
			}
		});
		this.socket.addErrorHandler(new Handler<String>() {

			@Override
			public void handle(String t) {
				//
				DefaultLogicalChannel.this.onError(t);
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
