/**
 *  Jan 31, 2013
 */
package org.cellang.webc.main.client;

import org.cellang.clwt.commons.client.AbstractCommonsControl;
import org.cellang.clwt.commons.client.UiCommonsConstants;
import org.cellang.clwt.commons.client.frwk.BodyViewI;
import org.cellang.clwt.core.client.Container;
import org.cellang.clwt.core.client.logger.WebLogger;
import org.cellang.clwt.core.client.logger.WebLoggerFactory;

/**
 * @author wuzhen
 * 
 */
public class LoginControlImpl extends AbstractCommonsControl implements LoginControlI {
	private static final WebLogger LOG = WebLoggerFactory.getLogger(LoginControlImpl.class);

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

		BodyViewI bv = this.getBodyView();
		LoginView lv = bv.getItem(UiCommonsConstants.LOGIN_VIEW, false);
		LOG.info("openLoginView,show:" + show + ",lv:" + lv);

		if (lv == null) {
			lv = new LoginView(this.getContainer(), "login");

			bv.addItem(UiCommonsConstants.LOGIN_VIEW, lv);
		}
		if (show) {
			bv.select(UiCommonsConstants.LOGIN_VIEW);//
		}
		return lv;
	}

	@Override
	public LoginViewI getLoginView() {
		
		return this.getBodyView().getItem(UiCommonsConstants.LOGIN_VIEW, false);//
	}

}
