package org.cellang.console;

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

		server = (Map<Keyword, Object>) ClojureOps.fnStartServer.invoke(ClojureOps.kwPort, port);
		LOG.info("started server");
	}

	public int getActualPort() {
		int port2 = (Integer) server.get(ClojureOps.kwPort);
		return port2;
	}

	public void close() {
		ClojureOps.fnStopServer.invoke(server);
		LOG.info("stoped server");
	}
}
