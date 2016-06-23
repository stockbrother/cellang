package org.cellang.webc.main.client.handler.action;

import org.cellang.clwt.commons.client.ActionHandlerSupport;
import org.cellang.clwt.commons.client.mvc.ActionEvent;
import org.cellang.clwt.commons.client.mvc.ControlManager;
import org.cellang.clwt.core.client.Container;
import org.cellang.clwt.core.client.data.ObjectPropertiesData;
import org.cellang.clwt.core.client.lang.Path;
import org.cellang.clwt.core.client.message.MsgWrapper;
import org.cellang.webc.main.client.LoginControlI;
import org.cellang.webc.main.client.LoginViewI;

/**
 * 
 * @author wuzhen
 *         <p>
 *         Submit the login email and password
 */
public class LoginSubmitActionHandler extends ActionHandlerSupport {

	/**
	 * @param c
	 */
	public LoginSubmitActionHandler(Container c) {
		super(c);
	}
	protected ControlManager getControlManager() {

		return (ControlManager)this.getClient(true).getProperty(ControlManager.class.getName(), true);
	}

	/*
	 * Jan 2, 2013
	 */
	@Override
	public void handle(ActionEvent ae) {
		//
		LoginControlI lc = this.getControlManager().getControl(LoginControlI.class, true);
		
		LoginViewI lm = lc.getLoginView();
		lm.clearErrorInfo();//

		String email = lm.getEmail();
		String password = lm.getPassword();

		MsgWrapper msg = new MsgWrapper(Path.valueOf("login.submit.request"));
		msg.setPayload("email", email);
		msg.setPayload("password", password);
		this.sendMessage(msg);
	}

}
