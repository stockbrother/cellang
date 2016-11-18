package org.cellang.viewsframework.clojure;

import clojure.lang.Keyword;
import clojure.lang.RT;
import clojure.lang.Symbol;
import clojure.lang.Var;

public class ClojureBridge {
	static {
		try {
			RT.var("clojure.core", "require").invoke(Symbol.intern("clojure.tools.nrepl"));
			RT.var("clojure.core", "require").invoke(Symbol.intern("clojure.tools.nrepl.server"));
			RT.var("clojure.core", "require").invoke(Symbol.intern("clojure.tools.nrepl.cmdline"));
			RT.var("clojure.core", "require").invoke(Symbol.intern("clojure.walk"));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static Var find(String ns, String name) {
		return Var.find(Symbol.intern(ns, name));
	}

	public static Var fnStartServer = find("clojure.tools.nrepl.server", "start-server");
	public static Var fnStopServer = find("clojure.tools.nrepl.server", "stop-server");
	public static Var fnCmdlineMain = find("clojure.tools.nrepl.cmdline", "-main");
	public static Var fnRead = find("clojure.core", "read");
	public static Var fnPrStr = find("clojure.core", "pr-str");
	
	public static Keyword kwPort = Keyword.find(null, "port");

}
