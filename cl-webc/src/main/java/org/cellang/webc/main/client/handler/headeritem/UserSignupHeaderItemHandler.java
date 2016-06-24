package org.cellang.webc.main.client.handler.headeritem;

import org.cellang.clwt.commons.client.frwk.HeaderItemEvent;
import org.cellang.clwt.core.client.Container;
import org.cellang.clwt.core.client.event.Event.EventHandlerI;
import org.cellang.webc.main.client.MainControlI;
import org.cellang.webc.main.client.handler.WebcHandlerSupport;

public class UserSignupHeaderItemHandler extends WebcHandlerSupport implements EventHandlerI<HeaderItemEvent> {

	public UserSignupHeaderItemHandler(Container c) {
		super(c);
	}

	@Override
	public void handle(HeaderItemEvent t) {
		MainControlI mc = this.getControl(MainControlI.class, true);
		mc.openSignup();
	}

}
