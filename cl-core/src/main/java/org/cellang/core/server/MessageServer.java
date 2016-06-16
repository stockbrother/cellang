package org.cellang.core.server;

import org.cellang.core.lang.MessageI;

public interface MessageServer {

	public void start();

	public void process(MessageI req);

	public void process(MessageI req, Channel channel);

	public void shutdown();

}
