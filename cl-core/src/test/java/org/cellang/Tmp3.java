package org.cellang;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;

import javax.swing.JFrame;

import org.cellang.core.beanshell.ConsoleUnescapeInputStream;

import bsh.util.JConsole;
import clojure.lang.Keyword;
import clojure.lang.RT;
import clojure.lang.Symbol;
import clojure.lang.Var;

public class Tmp3 {

	static {
		//setIn();
	}

	static {
		try {
			RT.var("clojure.core", "require").invoke(Symbol.intern("clojure.tools.nrepl.cmdline"));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static Var find(String ns, String name) {
		return Var.find(Symbol.intern(ns, name));
	}

	private static Var fnCmdlineMain = find("clojure.tools.nrepl.cmdline", "-main");

	private static Keyword kwPort = Keyword.find(null, "port");

	private static void setIn() {
		String fonts[] = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
		Font[] fts = new Font[fonts.length];
		for (int i = 0; i < fonts.length; i++) {
			System.out.print(fonts[i] + "\t--");
			fts[i] = Font.decode(fonts[i]);
			System.out.println(fts[i]);
		}
		// define a frame and add a console to it
		JFrame frame = new JFrame("JConsole example");

		JConsole console = new JConsole();

		// Font font = Font.decode("YouYuan");
		// System.out.println("font:" + font);//
		// console.setFont(font);
		// console.print
		frame.getContentPane().add(console);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600, 400);

		frame.setVisible(true);
		Reader input = new InputStreamReader(console.getInputStream(), Charset.forName("iso-8859-1"));

		System.setIn(new ConsoleUnescapeInputStream(input));
	}

	public static void main(String[] args) {

		// clojure.main.main(args);
		fnCmdlineMain.invoke("--interactive");
	}

}
