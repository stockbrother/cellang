package org.cellang.webc.test.client;

import org.cellang.clwt.core.client.data.MessageData;
import org.cellang.clwt.core.client.data.ObjectPropertiesData;
import org.cellang.clwt.core.client.event.AfterClientStartEvent;
import org.cellang.clwt.core.client.event.EndpointBondEvent;
import org.cellang.clwt.core.client.event.EndpointUnbondEvent;
import org.cellang.clwt.core.client.event.Event;
import org.cellang.clwt.core.client.event.Event.EventHandlerI;
import org.cellang.clwt.core.client.lang.Path;
import org.cellang.clwt.core.client.message.MessageHandlerI;
import org.cellang.clwt.core.client.message.MsgWrapper;
import org.cellang.clwt.core.client.transfer.Endpoint;

public class GwtTestSimpleClient extends AbstractGwtTestBase {

	private String nick = "user1";
	private String email = nick + "@domain.com";
	private String pass = nick;
	protected Endpoint endpoint;

	@Override
	protected void gwtSetUp() throws Exception {
		super.gwtSetUp();

		this.client.start();
	}

	public void testSignupClient() {
		System.out.println("GwtTestSimpleClient:testClient");

		this.finishing.add("start");
		this.finishing.add("signup");

		this.delayTestFinish(10 * 1000);
	}

	@Override
	protected void onEvent(Event e) {
		System.out.println("TestBase.onEvent(),e:" + e);
		if (e instanceof AfterClientStartEvent) {
			AfterClientStartEvent afe = (AfterClientStartEvent) e;

			this.onClientStart(afe);
		}
		if (e instanceof EndpointBondEvent) {
			this.onBond();
		} else if (e instanceof EndpointUnbondEvent) {
			this.onUnbond();
		}
	}

	private void onClientStart(AfterClientStartEvent afe) {
		this.tryFinish("start");//
		this.endpoint = this.client.getEndpoint(true);
		this.endpoint.addHandler(Path.valueOf("/endpoint/message/signup/request/success"),
				new MessageHandlerI<MsgWrapper>() {

					@Override
					public void handle(MsgWrapper t) {
						GwtTestSimpleClient.this.onSignupRequestSuccess(t);
					}
				});
		this.endpoint.addHandler(Path.valueOf("/endpoint/message/signup/confirm/success"),
				new MessageHandlerI<MsgWrapper>() {

					@Override
					public void handle(MsgWrapper t) {
						GwtTestSimpleClient.this.onSignupConfirmSuccess(t);
					}
				});
		this.signup(email, nick, pass);

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
