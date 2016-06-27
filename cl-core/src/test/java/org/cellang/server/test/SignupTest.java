package org.cellang.server.test;

import java.io.File;

import org.cellang.commons.lang.NameSpace;
import org.cellang.core.entity.EntityConfigFactory;
import org.cellang.core.lang.MessageI;
import org.cellang.core.lang.MessageSupport;
import org.cellang.core.server.MessageServer;
import org.cellang.core.server.DefaultCellangServer;
import org.cellang.core.server.MessageContext;
import org.cellang.core.server.Messages;
import org.cellang.core.server.QueueChannelProvider;
import org.cellang.core.server.QueueChannelProvider.QueueChannel;
import org.cellang.core.util.FileUtil;

import junit.framework.Assert;
import junit.framework.TestCase;

public class SignupTest extends TestCase {

	public void test() {

		File home = FileUtil.createTempDir("cl-test-home");
		QueueChannelProvider queuep = new QueueChannelProvider();
		QueueChannel qc = queuep.createChannel();
		MessageServer server = new DefaultCellangServer(home, queuep, new EntityConfigFactory());
		server.start();

		try {
			String email = "some-one@some-company.com";
			String password = "some-password";
			String nick = "some-nick";
			{

				MessageI msg = MessageSupport.newMessage(Messages.SIGNUP_REQ);
				msg.setChannelId(qc.getId());
				msg.setPayload("email", email);
				msg.setPayload("password", password);
				msg.setPayload("nick", nick);

				server.process(msg);
				MessageI rmsg = qc.queue.poll();

				Assert.assertNotNull(rmsg);
				rmsg.assertNoError();
			}
			{
				MessageI msg = MessageSupport.newMessage(Messages.LOGIN_REQ);
				msg.setChannelId(qc.getId());
				msg.setPayload("email", email);
				msg.setPayload("password", password);
				server.process(msg);
				MessageI res = qc.queue.poll();
				Assert.assertNotNull(res);
				res.assertNoError();

			}
			// login

		} finally {
			server.shutdown();
		}
	}
}
