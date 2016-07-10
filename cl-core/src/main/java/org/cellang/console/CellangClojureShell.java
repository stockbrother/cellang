package org.cellang.console;

import java.io.InputStreamReader;
import java.io.PushbackReader;
import java.io.Reader;
import java.util.List;
import java.util.Map;

import org.cellang.core.beanshell.ConsoleUnescapeInputStream;

import clojure.tools.nrepl.Connection.Response;

public class CellangClojureShell {

	protected CellangConsole console;
	private PushbackReader reader;
	private String ns = "user";

	private String read() {
		Object rt = ClojureOps.fnRead.invoke(this.reader);
		return (String) ClojureOps.fnPrStr.invoke(rt);
	}

	public static void main(String[] args) throws Exception {
		CellangClojureShell shell = new CellangClojureShell();
		shell.runShell();
	}

	public void runShell() throws Exception {

		this.console = new CellangConsole();

		int port = 7888;

		String bind = "localhost";
		ReplServer server = new ReplServer(port);
		server.start();
		try {
			int port2 = server.getActualPort();

			Reader input = console.getInput();
			this.reader = new PushbackReader(new InputStreamReader(new ConsoleUnescapeInputStream(input)));
			ReplClient client = new ReplClient(port2);

			client.connect();
			try {
				ReplSession session = client.newSession();
				this.console.println("sessionId:" + session.getId());//
				while (true) {
					this.console.print(this.ns + "- ");//
					String code = read();
					//this.console.println("" + code);//
					Response res = session.sendCode(code);
					this.println(res);//
				}

			} finally {
				client.close();
			}
		} finally {
			server.close();
		}

	}

	private void println(Response res) {
		Map<String, Object> map = res.combinedResponse();
		List<Object> value = (List<Object>) map.get("value");
		if (value != null) {
			this.console.print(value);
		}
		Object err = map.get("err");
		if (err != null) {
			this.console.print(err);
		}
		Object out = map.get("out");
		if (out != null) {
			this.console.print(out);
		}
		Object ns = map.get("ns");

		if (ns != null && !this.ns.equals(ns)) {
			this.ns = (String) ns;
		}

		this.console.println("");
	}

}
