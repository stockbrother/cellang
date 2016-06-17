/**
 *  Jan 31, 2013
 */
package org.cellang.webc.main.client.handler;

import java.util.List;
import java.util.Map;

import org.cellang.clwt.commons.client.EndpointKeeper;
import org.cellang.clwt.commons.client.frwk.FrwkViewI;
import org.cellang.clwt.commons.client.frwk.impl.EndpointBusyIndicator;
import org.cellang.clwt.core.client.ClientObject;
import org.cellang.clwt.core.client.Container;
import org.cellang.clwt.core.client.UiException;
import org.cellang.clwt.core.client.event.ClientStartedEvent;
import org.cellang.clwt.core.client.event.Event.EventHandlerI;
import org.cellang.clwt.core.client.gwtbridge.UiWindow;
import org.cellang.clwt.core.client.lang.Path;
import org.cellang.clwt.core.client.logger.WebLogger;
import org.cellang.clwt.core.client.logger.WebLoggerFactory;
import org.cellang.clwt.core.client.transfer.LogicalChannel;
import org.cellang.webc.main.client.WebcHandlerSupport;
import org.cellang.webc.main.client.event.AutoLoginRequireEvent;
import org.cellang.webc.main.client.handler.message.LoginFailureMH;
import org.cellang.webc.main.client.handler.message.LoginSuccessMH;

/**
 * @author wuzhen
 * 
 */
public class ClientStartedHandler extends WebcHandlerSupport implements EventHandlerI<ClientStartedEvent> {
	private static final WebLogger LOG = WebLoggerFactory.getLogger(ClientStartedHandler.class);

	/**
	 * @param c
	 */
	public ClientStartedHandler(Container c) {
		super(c);
	}

	@Override
	public void handle(ClientStartedEvent e) {
		LOG.info("handle-event:" + e);

		//
		EndpointBusyIndicator ebi = new EndpointBusyIndicator(this.container, e.getLogicalChannel());//
		ebi.parent(this.getRootView().find(FrwkViewI.class, true));//

		this.activeMessageHandlers(this.container, e.getClient());

		//
		// heatbeat
		EndpointKeeper ek = new EndpointKeeper(this.getClient(true));
		ek.start();//
		//
		String action = UiWindow.getParameter("action", null);
		if (action == null) {
			// TODO remove this event
			new AutoLoginRequireEvent(e.getSource()).dispatch();
		} else {
			Map<String, List<String>> pm = com.google.gwt.user.client.Window.Location.getParameterMap();
			if (action.equals("pf")) {
				// String pfId = UiWindow.getParameter("pfId", null);
				// if (pfId == null) {
				// throw new UiException("pfId is null");
				// }
				// LoginControlI lc = this.getControl(LoginControlI.class,
				// true);
				// PasswordResetViewI pv = lc.openPasswordResetView();
				// pv.setPfId(pfId);
				throw new UiException("todo");
			} else {
				throw new UiException("no this action:" + action);
			}
		}
	}

	public void activeMessageHandlers(Container c, ClientObject client) {
		LogicalChannel ep = client.getLogicalChannel(true);
		// ep.addHandler(Path.valueOf("/endpoint/message/signup/anonymous/success"),
		// new SignupAnonymousSuccessMH(c));
		//
		ep.addHandler("server-messages",Path.valueOf("/terminal/auth/success"), new LoginSuccessMH(c));
		ep.addHandler("server-messages",Path.valueOf("/terminal/auth/failure"), new LoginFailureMH(c));
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
