package org.cellang.console;

import java.io.IOException;

import clojure.tools.nrepl.Connection;

public class ReplClient {
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
	}

}
