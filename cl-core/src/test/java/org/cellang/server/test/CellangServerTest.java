package org.cellang.server.test;

import java.io.File;

import org.cellang.commons.lang.Path;
import org.cellang.core.lang.MessageI;
import org.cellang.core.lang.MessageSupport;
import org.cellang.core.server.CellangServer;
import org.cellang.core.server.DefaultCellangServer;
import org.cellang.core.server.MessageContext;
import org.cellang.core.server.Messages;
import org.cellang.core.util.FileUtil;

import junit.framework.Assert;
import junit.framework.TestCase;

public class CellangServerTest extends TestCase {

	public void test() {
		
		File home = FileUtil.createTempDir("cl-test-home");
		
		CellangServer server = new DefaultCellangServer(home);
		server.start();

		try {
			MessageI msg = MessageSupport.newMessage().path(Messages.AUTH_REQ);
			MessageI msg2 = MessageSupport.newMessage().path(Path.valueOf(msg.getPath().getParent(),"response"));
			String userId = "user1";
			String password = "password1";
			msg.setPayload("userId", userId);
			msg.setPayload("password", password);
			MessageContext mc = new MessageContext(msg,msg2,null);
			server.service(mc);
			MessageI rmsg = mc.getResponseMessage();
			Assert.assertNotNull(rmsg);
			rmsg.assertNoError();

		} finally {
			server.shutdown();
		}
	}
}
