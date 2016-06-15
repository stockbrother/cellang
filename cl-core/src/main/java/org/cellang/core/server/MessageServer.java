package org.cellang.core.server;

import org.cellang.core.lang.MessageI;

public interface MessageServer {

	public void start();

	public MessageI process(MessageI req);

	public MessageI process(MessageI req, Channel channel);

	public void shutdown();

}