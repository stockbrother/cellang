package org.cellang.console.clojure;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import clojure.lang.Keyword;

public class ReplServer {

	private static final Logger LOG = LoggerFactory.getLogger(ReplServer.class);

	public Map<Keyword, Object> server;

	public int port;

	public ReplServer(int port) {
		this.port = port;
	}

	public void start() {

		server = (Map<Keyword, Object>) ClojureBridge.fnStartServer.invoke(ClojureBridge.kwPort, port);
		LOG.info("repl server started.");
	}

	public int getActualPort() {
		int port2 = (Integer) server.get(ClojureBridge.kwPort);
		return port2;
	}

	public void close() {
		ClojureBridge.fnStopServer.invoke(server);
		LOG.info("repl server stoped.");
	}
}
