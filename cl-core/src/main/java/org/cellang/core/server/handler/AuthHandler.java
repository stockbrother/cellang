package org.cellang.core.server.handler;

import org.cellang.core.server.AbstracHandler;
import org.cellang.core.server.MessageContext;

public class AuthHandler extends AbstracHandler {

	@Override
	public void handle(MessageContext mc) {
		
		String userId = mc.getRequestMessage().getString("userId", true);
		String passWd = mc.getRequestMessage().getString("password", true);

		
	}

}
