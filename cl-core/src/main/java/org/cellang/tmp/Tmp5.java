package org.cellang.tmp;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import clojure.lang.Keyword;
import clojure.lang.RT;
import clojure.lang.Symbol;
import clojure.lang.Var;
import clojure.tools.nrepl.Connection;
import clojure.tools.nrepl.Connection.Response;

public class Tmp5 {
	static {
		try {
			RT.var("clojure.core", "require").invoke(Symbol.intern("clojure.tools.nrepl"));
			RT.var("clojure.core", "require").invoke(Symbol.intern("clojure.tools.nrepl.server"));
			RT.var("clojure.core", "require").invoke(Symbol.intern("clojure.walk"));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static Var find(String ns, String name) {
		return Var.find(Symbol.intern(ns, name));
	}

	private static Var fnStartServer = find("clojure.tools.nrepl.server", "start-server");
	private static Var fnStopServer = find("clojure.tools.nrepl.server", "stop-server");
	private static Keyword kwPort = Keyword.find(null, "port");

	public static void main(String[] args) throws Exception {
		int port = 7888;
		String bind = "localhost";
		Map<Keyword, Object> server = (Map<Keyword, Object>) fnStartServer.invoke(kwPort, port);
		Method[] ms = server.getClass().getDeclaredMethods();
		for (Method m : ms) {
			System.out.println(m);//
		}
		System.out.println("kwPort=" + kwPort);
		Map<String, Object> map2 = new HashMap<String, Object>();

		for (Keyword key : server.keySet()) {

			Object v = server.get(key);
			map2.put(key.getName(), v);//
			System.out.println("" + key.getNamespace() + "," + key.getName() + "=" + v);
		}

		System.out.println(map2);//
		int port2 = (Integer) server.get(kwPort);

		try {

			Connection conn = new Connection("nrepl://" + bind + ":" + port2, 1000);
			try {

				Response res = conn.send("op", "eval", "code", "(+ 1 1)");
				System.out.println(res.combinedResponse());//
			} finally {
				conn.close();
			}
		} finally {
			fnStopServer.invoke(server);
		}

	}
}
