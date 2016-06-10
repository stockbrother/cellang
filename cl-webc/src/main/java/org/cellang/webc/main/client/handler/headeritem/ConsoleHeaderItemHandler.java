package org.cellang.webc.main.client.handler.headeritem;

import org.cellang.clwt.commons.client.frwk.FrwkControlI;
import org.cellang.clwt.commons.client.frwk.HeaderItemEvent;
import org.cellang.clwt.core.client.Container;
import org.cellang.clwt.core.client.event.Event.EventHandlerI;
import org.cellang.webc.main.client.WebcHandlerSupport;

public class ConsoleHeaderItemHandler extends WebcHandlerSupport implements EventHandlerI<HeaderItemEvent> {

	public ConsoleHeaderItemHandler(Container c) {
		super(c);
	}

	@Override
	public void handle(HeaderItemEvent t) {
		
		FrwkControlI mc = this.getControl(FrwkControlI.class, true);
		mc.openConsoleView(true);//
		
	}

}
