/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 2, 2013
 */
package org.cellang.webc.main.client.handler.message;

import org.cellang.clwt.core.client.Container;
import org.cellang.clwt.core.client.data.ErrorInfosData;
import org.cellang.clwt.core.client.data.MessageData;
import org.cellang.clwt.core.client.message.MessageHandlerI;
import org.cellang.clwt.core.client.message.MsgWrapper;
import org.cellang.webc.main.client.AccountsLDW;
import org.cellang.webc.main.client.ErrorCodes;
import org.cellang.webc.main.client.UiResponse;
import org.cellang.webc.main.client.handler.WebcHandlerSupport;
import org.cellang.webc.main.client.widget.LoginViewI;

/**
 * @author wu
 * 
 */
public class LoginFailureMH extends WebcHandlerSupport implements MessageHandlerI<MsgWrapper> {

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
	public void handle(MsgWrapper t) {
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

			LoginViewI lv = (LoginViewI) this.container.getClient(true).getProperty(LoginViewI.class.getName(), true);
			lv.addErrorInfo(eis);
			return;
		}
		//is saved, auto login result,process it.
		//unknown error,cannot process.
		if( !eis.containsErrorCode(ErrorCodes.AUTH_FAILURE)){
			return;
		}
		

	}
}
