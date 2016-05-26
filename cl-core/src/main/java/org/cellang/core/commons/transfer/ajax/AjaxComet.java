/**
 *  
 */
package org.cellang.core.commons.transfer.ajax;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.cellang.core.commons.transfer.CometListener;
import org.cellang.core.commons.transfer.CometSupport;

/**
 * @author wu
 *         <p>
 *         The session of client connection.
 */
public class AjaxComet extends CometSupport {

	private BlockingQueue<AjaxMsg> queue;

	private long timeoutMs = 1000;

	// only one request exist for same time for processing,terminate the old
	// one for new one arrive.

	private AjaxRequestContext theRequestContext;

	private Object requestLock = new Object();

	public AjaxRequestContext getTheRequestContext() {
		return theRequestContext;
	}

	public void startRequest(AjaxRequestContext theRequestContext) {
		synchronized (this.requestLock) {

			if (this.theRequestContext != null) {
				//interrupt message will cause the current request finish
				this.putAjaxMessage(AjaxMsg.interruptMsg());//
			}

			//wait the current request finish,if it has not yet..
			while (this.theRequestContext != null) {// wait the old one finshed
				try {
					this.requestLock.wait();
				} catch (InterruptedException e) {

				}
			}
			this.theRequestContext = theRequestContext;
		}

	}

	public void endRequest() {
		synchronized (this.requestLock) {
			this.theRequestContext = null;
			this.requestLock.notifyAll();
		}
	}

	public AjaxComet(String tid) {
		super("ajax", tid);
		this.queue = new LinkedBlockingQueue<AjaxMsg>();
	}

	@Override
	public void sendMessage(String msg) {
		AjaxMsg am = new AjaxMsg(AjaxMsg.MESSAGE);
		am.setProperty(AjaxMsg.PK_TEXTMESSAGE, msg);
		this.putAjaxMessage(am);
	}

	public BlockingQueue<AjaxMsg> getQueue() {
		return queue;
	}

	public void putAjaxMessage(AjaxMsg am) {
		//
		try {
			this.queue.put(am);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}//
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.websocket.api.WebSocketI#addListener(com.fs.websocket.api.WsListenerI
	 * )
	 */
	@Override
	public void addListener(CometListener ln) {
		this.addListener(ln);
	}

	/**
	 * @return the timeoutMs
	 */
	public long getTimeoutMs() {
		return timeoutMs;
	}

}
