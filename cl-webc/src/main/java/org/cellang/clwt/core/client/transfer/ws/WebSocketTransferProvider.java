/**
 * All right is from Author of the file,to be explained in comming days.
 * May 9, 2013
 */
package org.cellang.clwt.core.client.transfer.ws;

import org.cellang.clwt.core.client.jso.CloseEventJSO;
import org.cellang.clwt.core.client.jso.ErrorJSO;
import org.cellang.clwt.core.client.jso.EventJSO;
import org.cellang.clwt.core.client.jso.WebSocketJSO;
import org.cellang.clwt.core.client.lang.Address;
import org.cellang.clwt.core.client.lang.Handler;
import org.cellang.clwt.core.client.logger.WebLogger;
import org.cellang.clwt.core.client.logger.WebLoggerFactory;
import org.cellang.clwt.core.client.transfer.AbstractUnderlyingTransfer;

/**
 * @author wu
 * 
 */
public class WebSocketTransferProvider extends AbstractUnderlyingTransfer {

	private WebLogger LOG = WebLoggerFactory.getLogger(WebSocketTransferProvider.class);

	private WebSocketJSO socket;

	/**
	 * @param wso
	 */
	public WebSocketTransferProvider(Address uri) {
		super(uri);
	}

	/**
	 * May 12, 2013
	 */
	protected void onMessage(EventJSO t) {
		//
		String msg = t.getData();
		this.msgHandlers.handle(msg);

	}

	/**
	 * May 12, 2013
	 */
	protected void onError(ErrorJSO t) {
		String msg = "" + t.getData();
		this.errorHandlers.handle(msg);
	}

	/**
	 * May 12, 2013
	 */
	protected void onClose(CloseEventJSO t) {
		//
		String msg = "" + t.getCode() + "," + t.getReason();
		this.closeHandlers.handle(msg);
	}

	private void onOpen(EventJSO evt) {
		this.opened();
	}

	/*
	 * May 9, 2013
	 */
	@Override
	public void open(long timeout) {
		super.open(timeout);
		// ws hs no open method,just when init to open it.

		String uriS = uri.getUri();

		WebSocketJSO wso = WebSocketJSO.newInstance(uriS, false);
		if (wso == null) {
			this.errorHandlers.handle("websocket not supported by browser?");
			return;
		}

		this.socket = wso;

		this.socket.onOpen(new Handler<EventJSO>() {

			@Override
			public void handle(EventJSO t) {
				WebSocketTransferProvider.this.onOpen(t);
			}
		});
		//
		this.socket.onClose(new Handler<CloseEventJSO>() {

			@Override
			public void handle(CloseEventJSO t) {
				WebSocketTransferProvider.this.onClose(t);
			}
		});

		//
		this.socket.onError(new Handler<ErrorJSO>() {

			@Override
			public void handle(ErrorJSO t) {
				WebSocketTransferProvider.this.onError(t);
			}
		});
		this.socket.onMessage(new Handler<EventJSO>() {

			@Override
			public void handle(EventJSO t) {
				WebSocketTransferProvider.this.onMessage(t);
			}
		});
	}

	/*
	 * May 9, 2013
	 */
	@Override
	public void close() {
		this.socket.close();
	}

	/*
	 * May 9, 2013
	 */
	@Override
	public void send(String jsS, Handler<String> onfailure) {

		// not supported failure callback.
		if (onfailure != null) {
			//
		}
		this.socket.send(jsS);
	}

	/*
	 * May 9, 2013
	 */
	@Override
	public boolean isOpen() {
		//
		return this.socket.isOpen();
	}

}
