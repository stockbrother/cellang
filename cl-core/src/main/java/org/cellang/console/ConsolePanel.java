package org.cellang.console;

import java.awt.Color;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PushbackReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import clojure.lang.RT;
import clojure.tools.nrepl.Connection.Response;

public class ConsolePanel extends BshConsole {

	public static interface ConsoleListener {
		public void sessionCreated(ReplSession session);
	}

	private PushbackReader reader;
	private String ns = "user";
	private ReplServer server;
	OperationContext oc;
	public List<ConsoleListener> listenerList = new ArrayList<ConsoleListener>();

	public ConsolePanel(OperationContext oc,int port) {
		this.oc = oc;
		this.server = new ReplServer(port);
	}

	public void addListener(ConsoleListener listener) {
		this.listenerList.add(listener);
	}

	public void start() {
		Runnable r = new Runnable() {
			public void run() {
				ConsolePanel.this.doRun();
			}
		};
		new Thread(r).start();
	}

	private void onSession(ReplSession session) {

		super.requestFocus();
		text.requestFocus();		
		RT.var("user", "oc", oc);
		for (ConsoleListener l : this.listenerList) {
			l.sessionCreated(session);//
		}
	}

	private void doRun() {
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