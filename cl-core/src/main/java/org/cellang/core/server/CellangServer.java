package org.cellang.core.server;

public interface CellangServer {

	public void start();

	public void service(MessageContext mc);

	public void shutdown();

}
