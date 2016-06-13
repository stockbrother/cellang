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
			String email = "some-one@some-company.com";
			String password = "some-password";
			String nick = "some-nick";
			{

				MessageI msg = MessageSupport.newMessage(Messages.SIGNUP_REQ);
				msg.setPayload("email", email);
				msg.setPayload("password", password);
				msg.setPayload("nick", nick);

				MessageI rmsg = server.process(msg);

				Assert.assertNotNull(rmsg);
				rmsg.assertNoError();
			}
			{
				MessageI msg = MessageSupport.newMessage(Messages.LOGIN_REQ);
				msg.setPayload("email",email);
				msg.setPayload("password",password);
				MessageI res = server.process(msg);
				Assert.assertNotNull(res);
				res.assertNoError();
				
			}
			// login

		} finally {
			server.shutdown();
		}
	}
}
