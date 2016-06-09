package org.cellang.webc.main.client.handler;

import org.cellang.clwt.commons.client.frwk.HeaderItemEvent;
import org.cellang.clwt.core.client.Container;
import org.cellang.clwt.core.client.event.Event.EventHandlerI;
import org.cellang.webc.main.client.LoginControlI;
import org.cellang.webc.main.client.WebcHandlerSupport;

public class UserLoginHeaderItemHandler extends WebcHandlerSupport implements EventHandlerI<HeaderItemEvent> {

	public UserLoginHeaderItemHandler(Container c) {
		super(c);		
	}

	@Override
	public void handle(HeaderItemEvent t) {
		LoginControlI lc = this.getControl(LoginControlI.class, true);
		lc.openLoginView(true);
	}

}
