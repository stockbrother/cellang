package org.cellang.webc.main.client;

import org.cellang.clwt.commons.client.mvc.Control;

public interface LoginControlI extends Control {

	/**
	 * @return
	 */
	public LoginViewI openLoginView(boolean show);
	
	//public PasswordResetViewI openPasswordResetView();

}
