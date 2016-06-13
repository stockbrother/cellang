package org.cellang.core.server;

import org.cellang.core.lang.MessageI;

public class MessageContext {

	private MessageI requestMessage;

	private MessageI responseMessage;

	private ServerContext serverContext;

	private Channel channel;
	
	public MessageContext(MessageI reqMsg, MessageI resMsg,Channel channel) {
		this.requestMessage = reqMsg;
		this.responseMessage = resMsg;
		this.channel = channel;
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

	public Channel getChannel() {
		return channel;
	}

	public void setResponseMessage(MessageI responseMessage) {
		this.responseMessage = responseMessage;
	}

}
