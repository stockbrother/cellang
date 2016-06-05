package org.cellang.core.server.handler;

import org.cellang.core.lang.MessageI;
import org.cellang.core.lang.MessageSupport;
import org.cellang.core.server.AbstracHandler;
import org.cellang.core.server.MessageContext;
import org.cellang.core.server.Messages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientIsReadyHandler extends AbstracHandler {
	private static final Logger LOG = LoggerFactory.getLogger(ClientIsReadyHandler.class);

	@Override
	public void handle(MessageContext t) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("handle");
		}
		MessageI msg = MessageSupport.newMessage(Messages.MSG_SERVER_IS_READY);
		t.getChannel().sendMessage(msg);
		if (LOG.isDebugEnabled()) {
			LOG.debug("end handle");
		}
	}

}
