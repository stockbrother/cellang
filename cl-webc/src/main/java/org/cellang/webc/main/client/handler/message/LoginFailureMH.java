/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 2, 2013
 */
package org.cellang.webc.main.client.handler.message;

import org.cellang.clwt.core.client.Container;
import org.cellang.clwt.core.client.UiException;
import org.cellang.clwt.core.client.data.ErrorInfosData;
import org.cellang.clwt.core.client.data.MessageData;
import org.cellang.clwt.core.client.event.LogicalChannelMessageEvent;
import org.cellang.clwt.core.client.message.MessageHandlerI;
import org.cellang.webc.main.client.AccountsLDW;
import org.cellang.webc.main.client.AnonymousAccountLDW;
import org.cellang.webc.main.client.ErrorCodes;
import org.cellang.webc.main.client.LoginViewI;
import org.cellang.webc.main.client.RegisteredAccountLDW;
import org.cellang.webc.main.client.WebcHandlerSupport;
import org.cellang.webc.main.client.UiResponse;
import org.cellang.webc.main.client.handler.action.AutoLoginHandler;

/**
 * @author wu
 * 
 */
public class LoginFailureMH extends WebcHandlerSupport implements MessageHandlerI<LogicalChannelMessageEvent> {

	/**
	 * @param c
	 */
	public LoginFailureMH(Container c) {
		super(c);
	}

	/*
	 * Jan 2, 2013
	 */
	@Override
	public void handle(LogicalChannelMessageEvent t) {
		MessageData res = t.getMessage();
		MessageData req = res.getSource();

		AccountsLDW accs = AccountsLDW.getInstance();
		ErrorInfosData eis = (ErrorInfosData) res.getPayload(UiResponse.ERROR_INFO_S);

		// the saved account/email/password not valid for some reason
		// 1)password is changed by some other means.
		// 2)annonymous account is removed by serve side for some reason.
		// then clean the saved info, and re run the procedure.
		Boolean isSaved = req.getBoolean("isSaved", true);
		//if not the 
		if (!isSaved) {//
			// not saved info,but user provided info,so notify user this
			// error.

			LoginViewI lv = this.getRootView().find(LoginViewI.class, true);
			lv.addErrorInfo(eis);
			return;
		}
		//is saved, auto login result,process it.
		//unknown error,cannot process.
		if( !eis.containsErrorCode(ErrorCodes.AUTH_FAILURE)){
			return;
		}
		
		String type = req.getString("type", true);
		if (type.equals("registered")) {//
			RegisteredAccountLDW acc1 = accs.getRegistered();
			acc1.invalid();// try using the anonymous login.
			// try auto auth with anonymous
			AutoLoginHandler.autoLogin(this.getEndpoint(), t.getSource());// try
																			// again,with
																			// anonymous
		} else if (type.equals("anonymous")) {
			AnonymousAccountLDW acc2 = accs.getAnonymous();
			acc2.invalid();// clean and try again: create a new
							// anonymous and login
			AutoLoginHandler.autoLogin(this.getEndpoint(), t.getSource());// try
																			// agin,
																			// signup
																			// anonymous
			// again

		} else {
			throw new UiException("bug,no this type:" + type);
		}

	}
}
