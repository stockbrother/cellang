/**
 * All right is from Author of the file,to be explained in comming days.
 * May 9, 2013
 */
package org.cellang.clwt.core.client.transfer.ajax;

import java.util.HashMap;
import java.util.Map;

import org.cellang.clwt.core.client.Container;
import org.cellang.clwt.core.client.Scheduler;
import org.cellang.clwt.core.client.UiException;
import org.cellang.clwt.core.client.lang.Address;
import org.cellang.clwt.core.client.lang.Handler;
import org.cellang.clwt.core.client.lang.Path;
import org.cellang.clwt.core.client.logger.WebLogger;
import org.cellang.clwt.core.client.logger.WebLoggerFactory;
import org.cellang.clwt.core.client.transfer.AbstractUnderlyingTransfer;
import org.cellang.clwt.core.client.util.ExceptionUtil;
import org.fusesource.restygwt.client.FailedStatusCodeException;
import org.fusesource.restygwt.client.JsonCallback;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.Resource;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;

/**
 * @author wu
 * 
 */
public class AjaxUnderlyingTransfer extends AbstractUnderlyingTransfer {

	private static final WebLogger LOG = WebLoggerFactory.getLogger(AjaxUnderlyingTransfer.class);

	// See serverlet in webserver
	public static final String HK_SESSION_ID = "x-cl-ajax-sessionId";

	private Resource resource;

	protected String sid;

	protected Map<Path, ClientAjaxHandler> handlers;

	protected ClientAjaxHandler defaultHandler;

	// private Timer heartBeatTimer;

	private Scheduler scheduler;

	private int requests;

	/**
	 * @param wso
	 */
	public AjaxUnderlyingTransfer(Container c, Address uri) {
		super(uri);
		this.scheduler = c.get(Scheduler.class, true);
		this.resource = new Resource(this.uri.getUri());

		//
		this.handlers = new HashMap<Path, ClientAjaxHandler>();
		this.handlers.put(AjaxMsgWrapper.CONNECT.getSubPath("success"), new ConnectedHandler(this));
		this.handlers.put(AjaxMsgWrapper.CLOSE.getSubPath("success"), new ClosedHandler(this));
		this.handlers.put(AjaxMsgWrapper.MESSAGE, new MessageHandler(this));
		this.handlers.put(AjaxMsgWrapper.ERROR, new ErrorHandler(this));

		this.defaultHandler = new DefaultClientHandler(this);

	}

	/*
	 * May 9, 2013
	 */
	@Override
	public void open(long timeout) {
		super.open(timeout);
		AjaxMsgWrapper am = new AjaxMsgWrapper(AjaxMsgWrapper.CONNECT);
		this.doRequest(am);

	}

	protected void doRequest(final AjaxMsgWrapper am) {
		this.doRequest(am, null);
	}

	protected void doRequest(final AjaxMsgWrapper am, final Handler<String> onfailure) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("doRequest:ajax msg:" + am);//
		}
		this.requests++;
		JSONArray jsa = new JSONArray();
		jsa.set(0, am.getAsJsonObject());
		final String text = jsa.toString();
		Method m = this.resource.post().text(text);
		// Content-Type: text/plain; charset=ISO-8859-1
		m.header("Content-Type", "application/json; charset=UTF-8");
		// m.header("Content-Length", "" + len(text));
		m.header("x-fs-debug", "debug:" + am.getPath());
		// session id is in request header.
		if (this.sid != null) {
			m.header(HK_SESSION_ID, this.sid);//
		}
		final String sid = this.sid;

		JsonCallback jcb = new JsonCallback() {

			@Override
			public void onFailure(Method method, Throwable exception) {
				//
				// TODO
				AjaxUnderlyingTransfer.this.onRequestFailure(onfailure, am, method, exception); //
			}

			@Override
			public void onSuccess(Method method, JSONValue response) {
				//

				AjaxUnderlyingTransfer.this.onRequestSuccess(am, method, response);
				//
			}
		};
		// NOTE: this will be blocking with GwtTest.
		m.send(jcb);
	}

	private int len(String text) {
		return text.getBytes().length;
	}

	/**
	 * @param method
	 * @param response
	 */
	protected void onRequestSuccess(AjaxMsgWrapper am, Method method, JSONValue response) {

		this.requests--;
		try {

			JSONArray jsa = (JSONArray) response;
			for (int i = 0; i < jsa.size(); i++) {
				JSONObject amS = (JSONObject) jsa.get(i);

				AjaxMsgWrapper am2 = new AjaxMsgWrapper(amS);

				this.onAjaxMsg(am2);
			}
		} finally {

			this.afterRequestSuccess(am);
		}
	}

	private void afterRequestSuccess(AjaxMsgWrapper am) {
		if (this.requests > 0) {// only schedule after the last request is
								// responsed.
			return;
		}

		if (this.isState(CLOSED) || this.isState(CLOSING)) {
			// if is closing or closed, no more things to do.
			// the close success handler will call the tryClose() method.
			return;
		}

		String oldSid = am.getSessionId(false);
		final String fsid = this.sid;
		if (oldSid == null && fsid != null) {// new session opened.

		}

		if (oldSid != null && fsid == null) {// old session closed.

		}

		if (fsid == null) {// is openning
			// not opened successfully before,must not send heart beat.
			// Just ignore?
			return;
		}
		// only for the last request,
		// no request for now, so do a new request
		// immediately.
		// each request finish,should schedule a new request immediately.
		// if (!this.isOpen()) {
		// // if not open for some error,how to do?
		// return;
		// }

		this.scheduler.scheduleDelay(new Handler<Object>() {

			@Override
			public void handle(Object t) {
				AjaxUnderlyingTransfer.this.doSendHeartBeat(fsid);
			}
		});
		//

	}

	/**
	 * @param method
	 * @param exception
	 */
	protected void onRequestFailure(Handler<String> onfailure, AjaxMsgWrapper req, Method method, Throwable exception) {
		this.requests--;

		String data = "request failure for request:" + req + ",now:" + System.currentTimeMillis();

		if (exception instanceof FailedStatusCodeException) {
			FailedStatusCodeException fsce = (FailedStatusCodeException) exception;
			int code = fsce.getStatusCode();
			data += ",failed state code:" + code;
		}
		data += ",exceptions:\n" + ExceptionUtil.getStacktraceAsString(exception, "\n");

		// app-level callback.
		if (onfailure != null) {
			onfailure.handle(data);
		}
		//
		this.errorHandlers.handle(data);

		// HOW this happen?,may be because the network issue?

		this.tryCloseByError();
	}

	/**
	 * @param am2
	 */
	private void onAjaxMsg(AjaxMsgWrapper am2) {
		Path path = am2.getPath();
		ClientAjaxHandler hdl = this.handlers.get(path);

		if (hdl == null) {
			hdl = this.defaultHandler;
		}
		ClientAjaxMsgContext amc = new ClientAjaxMsgContext();
		amc.am = am2;
		LOG.info("onAjaxMsg:" + am2);//
		hdl.handle(amc);

	}

	public void assertConnected() {
		if (!this.isConnected()) {
			throw new UiException("no sid");
		}
	}

	public boolean isConnected() {
		return null != this.sid;
	}

	/**
	 * Only send if connected,else stop the heart beat.
	 */
	protected boolean doSendHeartBeat(String sid) {

		// TODO,when test with GwtTest, send message to server will hang up the
		// client,and the event already scheduled/delayed is not able to be
		// handled.

		AjaxMsgWrapper am = new AjaxMsgWrapper(AjaxMsgWrapper.HEART_BEAT);
		this.doRequest(am);
		return true;
	}

	public void tryCloseByError() {
		this.tryClose();
	}

	/**
	 * 
	 */
	public void tryClosedByServer() {
		this.tryClose();
	}

	public void tryClose() {
		if (this.isState(CLOSED)) {
			// already closed,not raise event again.
			return;// duplicated close?
		}
		this.sid = null;
		this.closed();
		this.closeHandlers.handle("closed");
	}

	/**
	 * @param sid2
	 */
	public void conected(String sid2) {
		this.sid = sid2;
		this.opened();

	}

	/**
	 * 
	 */
	public void errorFromServer(String error, String msg) {
		this.errorHandlers.handle(error + "," + msg);
		if (AjaxMsgWrapper.ERROR_CODE_SESSION_NOTFOUND.equals(error)) {//
			this.tryCloseByError();
		}
	}

	public void messageFromServer(String msg) {
		this.msgHandlers.handle(msg);
	}

	/*
	 * May 9, 2013
	 */
	@Override
	public void close() {
		//
		if (!this.isState(OPENED)) {
			throw new UiException("not openned");
		}
		this.state = CLOSING;
		// TODO send a async message to server?
		AjaxMsgWrapper am = new AjaxMsgWrapper(AjaxMsgWrapper.CLOSE);
		this.doRequest(am);
	}

	/*
	 * May 9, 2013
	 */
	@Override
	public void send(String jsS, Handler<String> onfailure) {
		//
		AjaxMsgWrapper am = new AjaxMsgWrapper(AjaxMsgWrapper.MESSAGE);
		am.setProperty(AjaxMsgWrapper.PK_TEXTMESSAGE, jsS);
		this.doRequest(am, onfailure);
	}

	/*
	 * May 9, 2013
	 */
	@Override
	public boolean isOpen() {
		//
		return this.sid != null;
	}

}
