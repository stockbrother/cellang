package org.cellang.webc.main.client;

import org.cellang.clwt.commons.client.mvc.Control;
import org.cellang.webc.main.client.widget.LoginViewI;

public interface LoginControlI extends Control {

	/**
	 * @return
	 */
	public LoginViewI openLoginView(boolean show);
	
	public LoginViewI getLoginView();
	
	//public PasswordResetViewI openPasswordResetView();

}
