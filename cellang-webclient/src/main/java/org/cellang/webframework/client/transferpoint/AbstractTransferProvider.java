/**
 * All right is from Author of the file,to be explained in comming days.
 * May 9, 2013
 */
package org.cellang.webframework.client.transferpoint;

import org.cellang.webframework.client.lang.Address;
import org.cellang.webframework.client.lang.CollectionHandler;
import org.cellang.webframework.client.lang.Handler;
import org.cellang.webframework.client.lang.State;

import com.google.gwt.user.client.Timer;

/**
 * @author wu
 * 
 */
public abstract class AbstractTransferProvider implements TransferProvider {

	public static final State UNKNOWN = State.valueOf("unknown");

	public static final State OPENNING = State.valueOf("openning");

	public static final State OPENED = State.valueOf("opened");

	public static final State CLOSING = State.valueOf("closing");
	
	public static final State CLOSED = State.valueOf("closed");

	protected String protocol;

	protected Address uri;

	protected State state;

	protected boolean errorsInOpenning;

	protected CollectionHandler<TransferProvider> openHandlers = new CollectionHandler<TransferProvider>();
	protected CollectionHandler<String> closeHandlers = new CollectionHandler<String>();
	protected CollectionHandler<String> errorHandlers = new CollectionHandler<String>();
	protected CollectionHandler<String> msgHandlers = new CollectionHandler<String>();

	public AbstractTransferProvider(Address uri) {
		this.uri = uri;
		this.protocol = this.uri.getProtocol();
		// to process timeout error notify
		this.errorHandlers.addHandler(new Handler<String>() {

			@Override
			public void handle(String t) {
				AbstractTransferProvider.this.onError(t);
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
	
	protected void closed(){
		this.setState(CLOSED);
	}

	@Override
	public void open(long timeout) {

		this.setState(OPENNING);
		new Timer() {

			@Override
			public void run() {
				AbstractTransferProvider.this.checkOpenTimeout();
			}
		}.schedule((int) timeout);

	}

	protected void checkOpenTimeout() {

		if (!this.isState(OPENNING)) {//
			return;
		}

		// openning, but no error event .
		if (this.errorsInOpenning) {
			// already has error report,not report timeout?
		}

		//
		this.errorHandlers.handle("timeout to open:" + this.uri);
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
	public void addOpenHandler(final Handler<TransferProvider> handler) {
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
