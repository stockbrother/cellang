package org.cellang.server.test;

import org.cellang.core.lang.MessageI;
import org.cellang.core.lang.MessageSupport;
import org.cellang.core.server.CellangServer;
import org.cellang.core.server.DefaultCellangServer;
import org.cellang.core.server.MessageContext;
import org.cellang.core.server.Messages;

import junit.framework.Assert;
import junit.framework.TestCase;

public class CellangServerTest extends TestCase {

	public void test() {
		CellangServer server = new DefaultCellangServer();
		server.start();

		try {
			MessageI msg = MessageSupport.newMessage().path(Messages.MSG_AUTH);
			String userId = "user1";
			String password = "password1";
			msg.setPayload("userId", userId);
			msg.setPayload("password", password);
			MessageContext mc = new MessageContext(msg);
			server.service(mc);
			MessageI rmsg = mc.getResponseMessage();
			Assert.assertNotNull(rmsg);
			rmsg.assertNoError();

		} finally {
			server.shutdown();
		}
	}
}
