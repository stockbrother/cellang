package org.cellang.console;

import clojure.tools.nrepl.Connection.Response;

public class ReplSession {

	protected String sid;

	ReplClient client;

	public ReplSession(ReplClient client, String sid) {
		this.sid = sid;
		this.client = client;
	}

	public Response sendCode(String code) {
		return this.client.conn.sendSession(this.sid, "op", "eval", "code", code);//
	}

	public String getId() {
		return sid;
	}

}
