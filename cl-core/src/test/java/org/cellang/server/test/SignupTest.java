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

public class SignupTest extends TestCase {

	public void test() {
		
		File home = FileUtil.createTempDir("cl-test-home");
		
		CellangServer server = new DefaultCellangServer(home);
		server.start();

		try {
			MessageI msg = MessageSupport.newMessage().path(Messages.SIGNUP_REQ);
			msg.setPayload("email", "some-one@some-company.com");
			msg.setPayload("password","some-password");
			msg.setPayload("nick","some-nick");
			
			MessageI msg2 = MessageSupport.newMessage(Path.valueOf(Messages.SIGNUP_REQ.getParent(),"response"));
			
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
