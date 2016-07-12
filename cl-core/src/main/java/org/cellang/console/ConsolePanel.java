package org.cellang.console;

import java.awt.Color;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PushbackReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import clojure.lang.RT;
import clojure.tools.nrepl.Connection.Response;

public class ConsolePanel extends BshConsole {

	public static final String QUIT = "quit";
	public static final String EXIT = "exit";

	public static interface ConsoleListener {
		public void sessionCreated(ReplSession session);
	}

	private PushbackReader reader;
	private String ns = "user";
	private ReplServer server;
	OperationContext oc;
	public List<ConsoleListener> listenerList = new ArrayList<ConsoleListener>();

	public ConsolePanel(File dataDir, OperationContext oc, int port) {
		super(dataDir);
		this.oc = oc;
		this.server = new ReplServer(port);

	}

	public void addListener(ConsoleListener listener) {
		this.listenerList.add(listener);
	}

	private void onSession(ReplSession session) {

		super.requestFocus();
		text.requestFocus();
		RT.var("user", "oc", oc);
		for (ConsoleListener l : this.listenerList) {
			l.sessionCreated(session);//
		}
	}

	public void quit() {
		this.inputLine(QUIT);//
	}

	public void runLoop() {
		this.history.load();
		// repl start...
		server.start();
		try {
			int port2 = server.getActualPort();

			Reader input = new InputStreamReader(this.getInputStream());

			this.reader = new PushbackReader(new InputStreamReader(new Utf8CodedInputStream(input)));

			ReplClient client = new ReplClient(port2);

			client.connect();
			try {
				ReplSession session = client.newSession();
				this.onSession(session);//
				this.println("sessionId:" + session.getId());//

				while (true) {
					this.print(this.ns + "- ");//
					String code = read();
					if (QUIT.equals(code) || EXIT.equals(code)) {
						break;
					}
					Response res = session.sendCode(code);
					this.println(res);//
				}

				// this.close();
			} finally {
				client.close();
			}
		} finally {
			server.close();
		}
		this.history.save();		
	}

	private String read() {
		// read object
		Object rt = ClojureOps.fnRead.invoke(this.reader);
		// object print to string.
		return (String) ClojureOps.fnPrStr.invoke(rt);
	}

	private void println(Response res) {
		Map<String, Object> map = res.combinedResponse();
		List<Object> value = (List<Object>) map.get("value");
		if (value != null) {
			for (int i = 0; i < value.size(); i++) {
				Object vi = value.get(i);
				this.print(vi);
				if (i < value.size() - 1) {
					this.println();
				}
			}
		}
		Object err = map.get("err");
		if (err != null) {
			this.print(err, Color.red);
		}
		Object out = map.get("out");
		if (out != null) {
			this.print(out);
		}
		Object ns = map.get("ns");

		if (ns != null && !this.ns.equals(ns)) {
			this.ns = (String) ns;
		}

		this.println();
	}

}
