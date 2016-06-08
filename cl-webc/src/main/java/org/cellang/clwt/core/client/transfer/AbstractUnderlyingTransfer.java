/**
 * All right is from Author of the file,to be explained in comming days.
 * May 9, 2013
 */
package org.cellang.clwt.core.client.transfer;

import org.cellang.clwt.core.client.lang.Address;
import org.cellang.clwt.core.client.lang.CollectionHandler;
import org.cellang.clwt.core.client.lang.Handler;
import org.cellang.clwt.core.client.lang.State;

import com.google.gwt.user.client.Timer;

/**
 * @author wu
 * 
 */
public abstract class AbstractUnderlyingTransfer implements UnderlyingChannel {

	public static final State UNKNOWN = State.valueOf("unknown");

	public static final State OPENNING = State.valueOf("openning");

	public static final State OPENED = State.valueOf("opened");

	public static final State CLOSING = State.valueOf("closing");

	public static final State CLOSED = State.valueOf("closed");

	protected String protocol;

	protected Address uri;

	protected State state;

	protected boolean errorsInOpenning;

	protected CollectionHandler<UnderlyingChannel> openHandlers = new CollectionHandler<UnderlyingChannel>();
	protected CollectionHandler<String> closeHandlers = new CollectionHandler<String>();
	protected CollectionHandler<String> errorHandlers = new CollectionHandler<String>();
	protected CollectionHandler<String> msgHandlers = new CollectionHandler<String>();

	public AbstractUnderlyingTransfer(Address uri) {
		this.uri = uri;
		this.protocol = this.uri.getProtocol();
		// to process timeout error notify
		this.errorHandlers.addHandler(new Handler<String>() {

			@Override
			public void handle(String t) {
				AbstractUnderlyingTransfer.this.onError(t);
			}
		});

	}

	public String getProtocol() {
		return this.protocol;
	}

	public boolean isState(State s) {
		return this.state.equals(s);
	}

	public void setState(State s) {
		this.state = s;
	}

	protected void opened() {
		this.setState(OPENED);
		this.openHandlers.handle(this);
	}

	protected void closed() {
		this.setState(CLOSED);
	}

	@Override
	public void open(final long timeout) {

		this.setState(OPENNING);
		new Timer() {

			@Override
			public void run() {
				AbstractUnderlyingTransfer.this.checkOpenTimeout(timeout);
			}
		}.schedule((int) timeout);

	}

	protected void checkOpenTimeout(long timeout) {

		if (!this.isState(OPENNING)) {//
			return;
		}

		// openning, but no error event .
		if (this.errorsInOpenning) {
			// already has error report,not report timeout?
		}

		//
		this.errorHandlers.handle("timeout(" + timeout + ") to open:" + this.uri);
	}

	private void onError(String msg) {
		if (this.isState(OPENNING)) {// listen to check if has error in
										// openning.
			this.errorsInOpenning = true;
		}
	}

	/*
	 * May 9, 2013
	 */
	@Override
	public void addOpenHandler(final Handler<UnderlyingChannel> handler) {
		this.openHandlers.addHandler(handler);
	}

	/*
	 * May 9, 2013
	 */
	@Override
	public void addCloseHandler(final Handler<String> handler) {
		this.closeHandlers.addHandler(handler);
	}

	/*
	 * May 9, 2013
	 */
	@Override
	public void addErrorHandler(final Handler<String> handler) {
		this.errorHandlers.addHandler(handler);
	}

	/*
	 * May 9, 2013
	 */
	@Override
	public void addMessageHandler(final Handler<String> handler) {
		this.msgHandlers.addHandler(handler);
	}

}
