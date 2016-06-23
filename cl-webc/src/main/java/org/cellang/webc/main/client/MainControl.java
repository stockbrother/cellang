/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 17, 2012
 */
package org.cellang.webc.main.client;

import org.cellang.clwt.commons.client.CreaterI;
import org.cellang.clwt.commons.client.UiCommonsConstants;
import org.cellang.clwt.commons.client.frwk.FrwkControlI;
import org.cellang.clwt.core.client.Container;

/**
 * @author wu
 * 
 */
public class MainControl extends ControlSupport implements MainControlI {

	//ProfileModelI profile;

	/**
	 * @param name
	 */
	public MainControl(Container c, String name) {
		super(c, name);
		//this.profile = new ProfileModel("profile");

	}
//
//	public ProfileModelI getProfileModel() {
//		return this.profile;
//	}


	@Override
	public SignupViewI openSignup() {
		//

		//Window.alert("open signup view."); this may cause unexpected exception in Firefox console:"Negative entryDepth ... at entry -1"
		//
		SignupView rt = this.getOrCreateViewInBody(UiClientConstants.P_SIGNUP, new CreaterI<SignupView>() {

			@Override
			public SignupView create(Container ct) {
				//Window.alert("create signup view.");//
				return new SignupView(ct);
			}
		}, true);
		return rt;
	}

	@Override
	public void closeAll() {
		this.getBodyView().closeAllItems();
	}

	@Override
	public void closeSignup() {
		this.getBodyView().tryCloseItem(UiClientConstants.P_SIGNUP);
	}

	@Override
	public LoginViewI openLoginView(boolean show) {
		// 
		LoginControlI fc = this.getControl(LoginControlI.class, true);
		
		return fc.openLoginView(show);
	}

	@Override
	public void closeLoginView() {
		this.getBodyView().tryCloseItem(UiCommonsConstants.LOGIN_VIEW);
	}


}
