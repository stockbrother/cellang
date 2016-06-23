package org.cellang.webc.main.client.handler.action;

import org.cellang.clwt.commons.client.ActionHandlerSupport;
import org.cellang.clwt.commons.client.mvc.ActionEvent;
import org.cellang.clwt.core.client.Container;
import org.cellang.clwt.core.client.data.ObjectPropertiesData;
import org.cellang.clwt.core.client.lang.Path;
import org.cellang.clwt.core.client.message.MsgWrapper;
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

	/*
	 * Jan 2, 2013
	 */
	@Override
	public void handle(ActionEvent ae) {
		//

		LoginViewI lm = (LoginViewI) this.container.getClient(true).getProperty(LoginViewI.class.getName(), true);
		lm.clearErrorInfo();//

		String email = lm.getEmail();
		String password = lm.getPassword();

		MsgWrapper msg = new MsgWrapper(Path.valueOf("login.submit.request"));
		msg.setPayload("email", email);
		msg.setPayload("password", password);
		this.sendMessage(msg);
	}

}
