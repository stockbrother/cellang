package org.cellang.core.test;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.servlet.ServletException;

import org.cellang.commons.transfer.Comet;
import org.cellang.commons.transfer.CometListener;
import org.cellang.commons.transfer.ajax.AjaxCometServlet;

public class TestAjaxCometServlet extends AjaxCometServlet implements CometListener {
	public static class TestEvent {
		public String cometId;
		public String message;
	}

	public static final BlockingQueue<TestEvent> events = new LinkedBlockingQueue<TestEvent>();

	@Override
	public void init() throws ServletException {
		super.init();
		this.manager.addListener(this);
	}

	@Override
	public void onConnect(Comet ws) {

	}

	@Override
	public void onMessage(Comet ws, String ms) {
		TestEvent evt = new TestEvent();
		evt.cometId = ws.getId();
		evt.message = ms;
		events.add(evt);
	}

	@Override
	public void onException(Comet ws, Throwable t) {

	}

	@Override
	public void onClose(Comet ws, int statusCode, String reason) {

	}

}
