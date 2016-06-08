/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 2, 2013
 */
package org.cellang.webc.main.client.handler.message;

import org.cellang.clwt.core.client.Container;
import org.cellang.clwt.core.client.data.MessageData;
import org.cellang.clwt.core.client.event.LogicalChannelMessageEvent;
import org.cellang.clwt.core.client.message.MessageHandlerI;
import org.cellang.webc.main.client.AccountsLDW;
import org.cellang.webc.main.client.LoginViewI;
import org.cellang.webc.main.client.RegisteredAccountLDW;
import org.cellang.webc.main.client.UiHandlerSupport;

/**
 * @author wu
 * 
 */
public class LoginSuccessMH extends UiHandlerSupport implements MessageHandlerI<LogicalChannelMessageEvent> {

	/**
	 * @param c
	 */
	public LoginSuccessMH(Container c) {
		super(c);
	}

	/*
	 * Jan 2, 2013
	 */
	@Override
	public void handle(LogicalChannelMessageEvent t) {
		MessageData res = t.getMessage();

		AccountsLDW accs = AccountsLDW.getInstance();

		Boolean isSaved = res.getBoolean("isSaved", true);
		if (isSaved) {// // if by auto login,do nothing
			return;
		}
		String type = res.getString("type");

		// type must be registed.

		// is click by user.
		// not saved info,but user provided info,so notify user this
		// error.

		LoginViewI lv = this.getRootView().find(LoginViewI.class, true);
		if (!lv.isSavingAccount()) {
			return;
		}
		String email = res.getString("email", true);
		String password = res.getString("password", true);

		RegisteredAccountLDW acc1 = accs.getRegistered();
		acc1.save(email, password);
	}
}
