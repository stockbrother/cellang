package org.cellang.console;

import java.util.Map;

import clojure.lang.Keyword;

public class ReplServer {

	public Map<Keyword, Object> server;

	public int port;

	public ReplServer(int port) {
		this.port = port;
	}

	public void start() {
		server = (Map<Keyword, Object>) ClojureOps.fnStartServer.invoke(ClojureOps.kwPort, port);

	}

	public int getActualPort() {
		int port2 = (Integer) server.get(ClojureOps.kwPort);
		return port2;
	}

	public void close() {
		ClojureOps.fnStopServer.invoke(server);
	}
}
