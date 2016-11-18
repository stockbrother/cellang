package org.cellang.viewsframework.clojure;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import clojure.tools.nrepl.Connection;

public class ReplClient {
	private static final Logger LOG = LoggerFactory.getLogger(ReplClient.class);

	Connection conn;
	String bind = "localhost";
	int port;

	public ReplClient(int port) {
		this.port = port;
	}

	public void connect() {
		try {
			conn = new Connection("nrepl://" + bind + ":" + port, 1000);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		LOG.info("repl client connected.");
	}

	public ReplSession newSession() {
		String sid = this.conn.newSession(null);
		return new ReplSession(this, sid);

	}

	public void close() {
		try {
			this.conn.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		LOG.info("repl client closed.");
	}

}
