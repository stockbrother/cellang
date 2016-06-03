package org.cellang.commons.transfer.test.servlet;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;

import org.cellang.commons.lang.Handler;
import org.cellang.commons.transfer.Comet;
import org.cellang.commons.transfer.CometListener;
import org.cellang.commons.transfer.servlet.AjaxCometServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MockAjaxCometServlet extends AjaxCometServlet implements CometListener {

	private static final Logger LOG = LoggerFactory.getLogger(MockAjaxCometServlet.class);

	public static final String MSG_RESPONSE_no_handler_found = "no-handler-found";

	public static class TestEvent {
		public String cometId;
		public String message;
	}

	public static MockAjaxCometServlet ME = null;

	private Map<String, Handler<TestEvent>> handlerMap = new HashMap<String, Handler<TestEvent>>();

	@Override
	public void init() throws ServletException {
		super.init();
		this.manager.addListener(this);
		ME = this;
	}

	public Comet getComet(String id) {
		return this.manager.getComet(id);
	}

	public void addHandler(String cmd, Handler<TestEvent> hdl) {
		this.handlerMap.put(cmd, hdl);
	}

	@Override
	public void onConnect(Comet ws) {

	}

	@Override
	public void onMessage(Comet ws, String ms) {
		TestEvent evt = new TestEvent();
		evt.cometId = ws.getId();
		evt.message = ms;
		int idx = ms.indexOf(":");
		if (idx < 0) {
			throw new RuntimeException("illegal message:" + ms);
		}
		String cmd = ms.substring(0, idx);
		String args = ms.substring(idx + 1);
		Handler<TestEvent> hdl = this.handlerMap.get(cmd);
		if (hdl == null) {
			ws.sendMessage(MSG_RESPONSE_no_handler_found);
		} else {
			hdl.handle(evt);//
		}

	}

	@Override
	public void onException(Comet ws, Throwable t) {
		LOG.error("", t);
	}

	@Override
	public void onClose(Comet ws, int statusCode, String reason) {
		LOG.info("onClose" + ws.getId() + ",statusCode:" + statusCode + ",reason:" + reason);
	}

}
