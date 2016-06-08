package org.cellang.webc.test.client;

import org.cellang.clwt.core.client.data.MessageData;
import org.cellang.clwt.core.client.data.ObjectPropertiesData;
import org.cellang.clwt.core.client.event.ClientStartedEvent;
import org.cellang.clwt.core.client.event.LogicalChannelBondEvent;
import org.cellang.clwt.core.client.event.LogicalChannelUnbondEvent;
import org.cellang.clwt.core.client.event.Event;
import org.cellang.clwt.core.client.lang.Path;
import org.cellang.clwt.core.client.logger.WebLoggerFactory;
import org.cellang.clwt.core.client.message.MessageHandlerI;
import org.cellang.clwt.core.client.message.MsgWrapper;
import org.cellang.clwt.core.client.transfer.LogicalChannel;

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
		this.client.start();
		System.out.println("testSignupClient");
		this.finishing.add("signup");
		//
		
		this.delayTestFinish(10 * 1000);
	}

	@Override
	protected void onEvent(Event e) {
		System.out.println("TestBase.onEvent(),e:" + e);
		if (e instanceof ClientStartedEvent) {
			ClientStartedEvent afe = (ClientStartedEvent) e;

			this.onClientStart(afe);
		}
		if (e instanceof LogicalChannelBondEvent) {
			this.onBond();
		} else if (e instanceof LogicalChannelUnbondEvent) {
			this.onUnbond();
		}
	}

	private void onClientStart(ClientStartedEvent afe) {
		this.tryFinish("start");//
		this.endpoint = this.client.getLogicalChannel(true);		
		
	}

	protected MsgWrapper newRequest(String path) {
		return new MsgWrapper(Path.valueOf(path));
	}

	protected void signup(String email, String nick, String pass) {
		MsgWrapper req = newRequest("/signup/submit");
		req.setPayload("email", email);
		req.setPayload("nick", nick);
		req.setPayload("password", pass);//
		req.setPayload("isAgree", Boolean.TRUE);//
		req.setPayload("confirmCodeNotifier", "resp");//

		this.endpoint.sendMessage(req);
	}

	protected void onSignupRequestSuccess(MsgWrapper evt) {
		MessageData t = evt.getTarget();
		String ccode = t.getString("confirmCode", true);
		MsgWrapper req = this.newRequest("/signup/confirm");
		req.setPayload("email", email);
		req.setPayload("confirmCode", ccode);

		this.endpoint.sendMessage(req);
	}

	protected void onSignupConfirmSuccess(MsgWrapper evt) {

	}

	public void onBond() {
		ObjectPropertiesData ui = this.endpoint.getUserInfo();
		assertNotNull("user info is null", ui);
		// ui.getAccountId();
		// ui.getSessionId();
		// ui.isAnonymous();
		this.tryFinish("bond");
		// this.mc.logout();
	}

	public void onUnbond() {
		ObjectPropertiesData ui = this.endpoint.getUserInfo();
		assertNull("user info is not null", ui);
		this.tryFinish("unbond");
	}

}
