package org.cellang.core.server;

import org.cellang.core.lang.MessageI;

public interface CellangServer {

	public void start();

	public MessageI process(MessageI req);

	public void service(MessageContext mc);

	public void shutdown();

}
