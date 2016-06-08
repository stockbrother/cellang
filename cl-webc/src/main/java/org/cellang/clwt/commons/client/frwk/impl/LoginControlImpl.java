/**
 *  Jan 31, 2013
 */
package org.cellang.clwt.commons.client.frwk.impl;

import org.cellang.clwt.commons.client.UiCommonsConstants;
import org.cellang.clwt.commons.client.frwk.BodyViewI;
import org.cellang.clwt.core.client.Container;
import org.cellang.webc.main.client.AbstractWebcControl;
import org.cellang.webc.main.client.LoginControlI;
import org.cellang.webc.main.client.LoginViewI;

/**
 * @author wuzhen
 * 
 */
public class LoginControlImpl extends AbstractWebcControl implements LoginControlI {

	/**
	 * @param name
	 */
	public LoginControlImpl(Container c, String name) {
		super(c, name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uicommons.api.gwt.client.frwk.login.LoginControlI#openLoginView()
	 */
	@Override
	public LoginViewI openLoginView(boolean show) {

		// TODO creater
		BodyViewI bv = this.getBodyView();
		LoginView lv = bv.getItem(UiCommonsConstants.LOGIN_VIEW, false);
		if (lv == null) {
			lv = new LoginView(this.getContainer(), "login");

			bv.addItem(UiCommonsConstants.LOGIN_VIEW, lv);
		}
		if(show){
			bv.select(UiCommonsConstants.LOGIN_VIEW);//			
		}
		return lv;
	}

}
