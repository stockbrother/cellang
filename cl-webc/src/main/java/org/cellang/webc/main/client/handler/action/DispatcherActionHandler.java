package org.cellang.webc.main.client.handler.action;

import org.cellang.clwt.commons.client.mvc.ActionEvent;
import org.cellang.clwt.core.client.Container;
import org.cellang.clwt.core.client.event.Event.EventHandlerI;
import org.cellang.clwt.core.client.lang.DispatcherImpl;
import org.cellang.clwt.core.client.lang.Path;
import org.cellang.clwt.core.client.lang.PathBasedDispatcher;
import org.cellang.clwt.core.client.logger.WebLogger;
import org.cellang.clwt.core.client.logger.WebLoggerFactory;
import org.cellang.webc.main.client.Actions;
import org.cellang.webc.main.client.handler.WebcHandlerSupport;

public class DispatcherActionHandler extends WebcHandlerSupport implements EventHandlerI<ActionEvent> {
	private static final WebLogger LOG = WebLoggerFactory.getLogger(DispatcherActionHandler.class);

	private PathBasedDispatcher dispatcher;

	public DispatcherActionHandler(Container c) {
		super(c);
		this.dispatcher = new DispatcherImpl("action-dispatching-handler");
		this.dispatcher.addHandler(Actions.A_SIGNUP_SUBMIT, new SignupSubmitActionHandler(c));
		this.dispatcher.addHandler(Actions.A_LOGIN_SUBMIT, new LoginSubmitActionHandler(c));
		
	}

	@Override
	public void handle(ActionEvent t) {
		Path p = t.getActionPath();

		LOG.debug("action event, action path:" + p);

		this.dispatcher.dispatch(p, t);//
	}

}
