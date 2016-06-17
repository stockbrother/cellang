package org.cellang.webc.test.client;

import org.cellang.clwt.core.client.data.MessageData;
import org.cellang.clwt.core.client.event.ClientStartedEvent;
import org.cellang.clwt.core.client.event.Event;
import org.cellang.clwt.core.client.event.LogicalChannelMessageEvent;
import org.cellang.clwt.core.client.lang.AbstractWebObject;
import org.cellang.clwt.core.client.lang.CollectionHandler;
import org.cellang.clwt.core.client.lang.DispatcherImpl;
import org.cellang.clwt.core.client.lang.HandlerEntry;
import org.cellang.clwt.core.client.lang.Path;
import org.cellang.clwt.core.client.logger.WebLogger;
import org.cellang.clwt.core.client.logger.WebLoggerFactory;
import org.cellang.clwt.core.client.message.MsgWrapper;
import org.cellang.clwt.core.client.transfer.AbstractLogicalChannel;
import org.cellang.clwt.core.client.transfer.LogicalChannel;
import org.cellang.webc.main.client.Messages;

public class SignupGwtTest extends AbstractGwtTestBase2 {

	private String nick = "user1";
	private String email = nick + "@domain.com";
	private String pass = nick;
	protected LogicalChannel endpoint;

	@Override
	protected void gwtSetUp() throws Exception {
		super.gwtSetUp();
	}

	public void testSignupClient() {
		WebLoggerFactory.configure(WebLogger.LEVEL_DEBUG);//
		WebLoggerFactory.configure(AbstractLogicalChannel.class,WebLogger.LEVEL_TRACE);
		WebLoggerFactory.configure(AbstractWebObject.class,WebLogger.LEVEL_TRACE);
		WebLoggerFactory.configure(DispatcherImpl.class,WebLogger.LEVEL_TRACE);
		WebLoggerFactory.configure(CollectionHandler.class,WebLogger.LEVEL_TRACE);
		WebLoggerFactory.configure(HandlerEntry.class,WebLogger.LEVEL_TRACE);
		
		
		
		
		this.client.start();
		System.out.println("testSignupClient");
		this.finishing.add("start");
		this.finishing.add("signup");
		//

		this.delayTestFinish(10 * 1000);
	}

	@Override
	protected void onEvent(Event e) {
		System.out.println("TestBase.onEvent(),e:" + e);
		/**
		if (e instanceof ClientStartedEvent) {
			ClientStartedEvent afe = (ClientStartedEvent) e;

			this.onClientStart(afe);
		} else if (e instanceof LogicalChannelMessageEvent) {
			LogicalChannelMessageEvent me = (LogicalChannelMessageEvent) e;
			MessageData md = me.getChannelMessageData();
			if (Messages.SIGNUP_RES.equals(md.getPath())) {
				this.onSignupConfirmSuccess();
			}

		}
**/
	}

	private void onClientStart(ClientStartedEvent afe) {
		this.tryFinish("start");//
		this.endpoint = this.client.getLogicalChannel(true);
		this.signup(email, nick, pass);
	}

	protected MsgWrapper newRequest(String path) {
		return new MsgWrapper(Path.valueOf(path));
	}

	protected void signup(String email, String nick, String pass) {
		MsgWrapper req = newRequest(Messages.SIGNUP_REQ.toString());

		req.setPayload("email", email);
		req.setPayload("nick", nick);
		req.setPayload("password", pass);//
		this.endpoint.sendMessage(req);
	}

	protected void onSignupRequestSuccess(MsgWrapper evt) {

		MsgWrapper req = this.newRequest(Messages.LOGIN_REQ.toString());
		req.setPayload("email", email);
		req.setPayload("password", pass);

		this.endpoint.sendMessage(req);
	}

	protected void onSignupConfirmSuccess() {
		this.tryFinish("signup");
	}

}
