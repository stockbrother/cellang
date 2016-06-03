package org.cellang.commons.transfer.test;

import java.util.concurrent.TimeUnit;

import org.cellang.commons.transfer.Comet;
import org.cellang.commons.transfer.ajax.AjaxMsg;
import org.cellang.commons.transfer.test.mock.ClientWithBrowserMock;
import org.cellang.commons.transfer.test.servlet.MockAjaxCometServlet;
import org.junit.Assert;

import junit.framework.TestCase;

public class AjaxCometServletTest extends TestCase {

	public AjaxCometServletTest() {
	}

	ClientWithBrowserMock browser;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		browser = new ClientWithBrowserMock();
	}

	public void testNoThisHandler() throws Exception {
		System.out.println("start testNoThisHandler");
		String sessionId = browser.syncConnect();
		try {
			int seq = 0;

			this.asyncSendNewMessage("no_this_handler", seq++, sessionId);

			AjaxMsg am = this.browser.queueCallback.queue.poll(1, TimeUnit.SECONDS);
			Assert.assertNotNull("timeout to get response", am);

			Assert.assertEquals("message not metaInfo.", MockAjaxCometServlet.MSG_RESPONSE_no_handler_found,
					am.getProperty(AjaxMsg.PK_TEXTMESSAGE));

		} finally {
			browser.syncClose(sessionId);
		}
		System.out.println("end testNoThisHandler");
	}

	public void testHeartBeatAndLongConnection() throws Exception {
		System.out.println("start testHeatBeat");
		String sessionId = browser.syncConnect();
		try {
			// send heartbeat,should blocking for awhile(the first message
			// timeout).
			this.browser.asyncSendHeartBeat(sessionId);

			AjaxMsg am = null;
			am = this.browser.queueCallback.queue.poll(1, TimeUnit.SECONDS);
			Assert.assertNull("get an unexpected response", am);

			// send message from server side
			Comet cmt = MockAjaxCometServlet.ME.getComet(sessionId);
			cmt.sendMessage("server-message-1");

			// for http long connection,this message should be sent to client
			// side.
			am = this.browser.queueCallback.queue.poll(1, TimeUnit.SECONDS);
			Assert.assertNotNull("not received message from server", am);

			// no http connection now, so this message from server side cannot
			// be received by client.
			cmt.sendMessage("server-message-2");
			am = this.browser.queueCallback.queue.poll(1, TimeUnit.SECONDS);
			Assert.assertNull("get an unexpected response", am);
			// send another message,and receive two message from server(1 is
			// old, 1 is new)
			this.asyncSendNewMessage("no_this_handler", 1, sessionId);
			am = this.browser.queueCallback.queue.poll(1, TimeUnit.SECONDS);
			Assert.assertNotNull("server-message-2 not received", am);
			Assert.assertEquals("server-message-2", am.getProperty(AjaxMsg.PK_TEXTMESSAGE));
			am = this.browser.queueCallback.queue.poll(1, TimeUnit.SECONDS);
			Assert.assertNotNull("timeout to get response", am);
			Assert.assertEquals("message not metaInfo.", MockAjaxCometServlet.MSG_RESPONSE_no_handler_found,
					am.getProperty(AjaxMsg.PK_TEXTMESSAGE));

		} finally {
			browser.syncClose(sessionId);
		}
		System.out.println("end testHeatBeat");
	}

	private AjaxMsg asyncSendNewMessage(String prefix, int seq, String sessionId) {
		AjaxMsg rt = this.newMessage(prefix, seq);
		browser.asyncSend(rt, sessionId);
		return rt;
	}

	private AjaxMsg newMessage(String cmd, int seq) {
		AjaxMsg amsg = new AjaxMsg(AjaxMsg.MESSAGE);
		String msg = cmd + ":" + seq;
		amsg.setProperty(AjaxMsg.PK_TEXTMESSAGE, msg);
		return amsg;
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();

	}

}
