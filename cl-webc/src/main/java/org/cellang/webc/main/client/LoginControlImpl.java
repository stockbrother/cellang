/**
 *  Jan 31, 2013
 */
package org.cellang.webc.main.client;

import org.cellang.clwt.commons.client.AbstractCommonsControl;
import org.cellang.clwt.commons.client.UiCommonsConstants;
import org.cellang.clwt.commons.client.frwk.BodyViewI;
import org.cellang.clwt.core.client.Container;

/**
 * @author wuzhen
 * 
 */
public class LoginControlImpl extends AbstractCommonsControl implements LoginControlI {

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
