package org.cellang.webc.main.client.handler.action;

import org.cellang.clwt.core.client.Container;
import org.cellang.clwt.core.client.data.ObjectPropertiesData;
import org.cellang.clwt.core.client.event.Event.EventHandlerI;
import org.cellang.clwt.core.client.lang.Path;
import org.cellang.clwt.core.client.lang.WebObject;
import org.cellang.clwt.core.client.message.MsgWrapper;
import org.cellang.clwt.core.client.transfer.LogicalChannel;
import org.cellang.webc.main.client.AccountsLDW;
import org.cellang.webc.main.client.AnonymousAccountLDW;
import org.cellang.webc.main.client.LoginControlI;
import org.cellang.webc.main.client.RegisteredAccountLDW;
import org.cellang.webc.main.client.WebcHandlerSupport;
import org.cellang.webc.main.client.event.AutoLoginRequireEvent;

/**
 * 
 * @author wuzhen
 *         <p>
 *         Submit the login email and password
 */
public class AutoLoginHandler extends WebcHandlerSupport implements EventHandlerI<AutoLoginRequireEvent> {

	/**
	 * @param c
	 */
	public AutoLoginHandler(Container c) {
		super(c);
	}

	/*
	 * Jan 2, 2013
	 */
	@Override
	public void handle(AutoLoginRequireEvent ae) {
		//
		LoginControlI lc = this.getControl(LoginControlI.class, true);
		LogicalChannel ep = this.getEndpoint();
		AutoLoginHandler.autoLogin(ep, ae.getSource());

	}

	public static void autoLogin(LogicalChannel endpoint, WebObject esource) {
		ObjectPropertiesData req = new ObjectPropertiesData();

		// this submit

		AccountsLDW accs = AccountsLDW.getInstance();
		RegisteredAccountLDW acc1 = accs.getRegistered();
		AnonymousAccountLDW acc2 = accs.getAnonymous();
		if (acc1.isValid()) {//registered user is available
			req.setProperty("isSaved", (true));
			req.setProperty("type", ("registered"));
			req.setProperty("email", (acc1.getEmail()));
			req.setProperty("password", (acc1.getPassword()));
		} else if (acc2.isValid()) {//anonymous user is available
			req.setProperty("isSaved", (true));
			req.setProperty("type", ("anonymous"));

			String accId = acc2.getAccountId();

			String password = acc2.getPassword();
			req.setProperty("accountId", (accId));
			req.setProperty("password", (password));

		} else {// has not saved account,create it first and then call this
				// submit again.
			MsgWrapper msg = new MsgWrapper(Path.valueOf("/signup/anonymous"));
			endpoint.sendMessage(msg);
			return;
		}

		endpoint.auth(req);
	}

}
