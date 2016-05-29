package org.cellang.core.server;

import org.cellang.core.lang.MessageI;
import org.cellang.core.lang.MessageSupport;

public class MessageContext {

	private MessageI requestMessage;

	private MessageI responseMessage;

	private ServerContext serverContext;

	public MessageContext(MessageI reqMsg) {
		this.requestMessage = reqMsg;
		this.responseMessage = MessageSupport.newMessage();
	}

	public MessageI getRequestMessage() {
		return requestMessage;
	}

	public MessageI getResponseMessage() {
		return responseMessage;
	}

	public ServerContext getServerContext() {
		return serverContext;
	}

	public void setServerContext(ServerContext serverContext) {
		this.serverContext = serverContext;
	}

}
