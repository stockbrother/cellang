package org.cellang.webc.main.client.handler.action;

import org.cellang.clwt.commons.client.ActionHandlerSupport;
import org.cellang.clwt.commons.client.mvc.ActionEvent;
import org.cellang.clwt.core.client.Container;
import org.cellang.clwt.core.client.data.ObjectPropertiesData;
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

		LoginViewI lm = this.findView(LoginViewI.class, true);
		lm.clearErrorInfo();//
		ObjectPropertiesData req = new ObjectPropertiesData();

		// this submit

		req.setProperty("type", ("registered"));
		req.setProperty("isSaved", Boolean.FALSE);

		String email = lm.getEmail();

		String password = lm.getPassword();
		req.setProperty("email", (email));
		req.setProperty("password", (password));
		
		this.getEndpoint().auth(req);
	}

}
