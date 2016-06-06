/**
 *  Jan 31, 2013
 */
package org.cellang.webc.main.client.handler;

import java.util.List;
import java.util.Map;

import org.cellang.clwt.commons.client.EndpointKeeper;
import org.cellang.clwt.commons.client.frwk.FrwkControlI;
import org.cellang.clwt.core.client.Container;
import org.cellang.clwt.core.client.UiException;
import org.cellang.clwt.core.client.WebClient;
import org.cellang.clwt.core.client.event.AfterClientStartEvent;
import org.cellang.clwt.core.client.event.Event.EventHandlerI;
import org.cellang.clwt.core.client.gwtbridge.UiWindow;
import org.cellang.clwt.core.client.lang.Path;
import org.cellang.clwt.core.client.logger.WebLogger;
import org.cellang.clwt.core.client.logger.WebLoggerFactory;
import org.cellang.clwt.core.client.transfer.Endpoint;
import org.cellang.webc.main.client.AutoLoginRequireEvent;
import org.cellang.webc.main.client.HeaderItems;
import org.cellang.webc.main.client.UiHandlerSupport;
import org.cellang.webc.main.client.handler.message.LoginFailureMH;
import org.cellang.webc.main.client.handler.message.LoginSuccessMH;

/**
 * @author wuzhen
 * 
 */
public class ClientStartedHandler extends UiHandlerSupport implements EventHandlerI<AfterClientStartEvent> {
	private static final WebLogger LOG = WebLoggerFactory.getLogger(ClientStartedHandler.class);
	/**
	 * @param c
	 */
	public ClientStartedHandler(Container c) {
		super(c);
	}

	@Override
	public void handle(AfterClientStartEvent e) {
		LOG.debug("handle event:"+e);
		
		this.activeMessageHandlers(this.container, e.getClient());

		//
		// heatbeat
		EndpointKeeper ek = new EndpointKeeper(this.getClient(true));
		ek.start();//

		// start frwk view.
		FrwkControlI fc = this.getControl(FrwkControlI.class, true);
		fc.open();
		fc.addHeaderItem(HeaderItems.USER_LOGIN);
		//
		String action = UiWindow.getParameter("action", null);
		if (action == null) {
			// TODO remove this event
			new AutoLoginRequireEvent(e.getSource()).dispatch();
		} else {
			Map<String, List<String>> pm = com.google.gwt.user.client.Window.Location.getParameterMap();
			if (action.equals("pf")) {
//				String pfId = UiWindow.getParameter("pfId", null);
//				if (pfId == null) {
//					throw new UiException("pfId is null");
//				}
//				LoginControlI lc = this.getControl(LoginControlI.class, true);
//				PasswordResetViewI pv = lc.openPasswordResetView();
//				pv.setPfId(pfId);
				throw new UiException("todo");
			} else {
				throw new UiException("no this action:" + action);
			}
		}
	}

	public void activeMessageHandlers(Container c, WebClient client) {
		Endpoint ep = client.getEndpoint(true);
		// ep.addHandler(Path.valueOf("/endpoint/message/signup/anonymous/success"),
		// new SignupAnonymousSuccessMH(c));
		//
		ep.addHandler(Path.valueOf("/endpoint/message/terminal/auth/success"), new LoginSuccessMH(c));
		ep.addHandler(Path.valueOf("/endpoint/message/terminal/auth/failure"), new LoginFailureMH(c));
		// ep.addHandler(Path.valueOf("/endpoint/message/password/forgot/success"),
		// new PasswordForgotSuccessMH(
		// c));
		// ep.addHandler(Path.valueOf("/endpoint/message/password/forgot/failure"),
		// new PasswordForgotFailureMH(
		// c));
		// ep.addHandler(Path.valueOf("/endpoint/message/password/reset/failure"),
		// new PasswordResetFailureMH(c));
		// ep.addHandler(Path.valueOf("/endpoint/message/password/reset/success"),
		// new PasswordResetSuccessMH(c));

	}

}
