package org.cellang;

import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;

import javax.swing.JFrame;

import org.cellang.commons.util.StringUtil;

import bsh.Capabilities;
import bsh.EvalError;
import bsh.Interpreter;
import bsh.util.JConsole;

public class Tmp2 {

	public static void main(String args[]) {

		if (!Capabilities.classExists("bsh.util.Util"))
			System.out.println("Can't find the BeanShell utilities...");

		if (Capabilities.haveSwing()) {
			bsh.util.Util.startSplashScreen();
			try {
				new Interpreter().eval("desktop()");
			} catch (EvalError e) {
				System.err.println("Couldn't start desktop: " + e);
			}
		} else {
			System.err.println("Can't find javax.swing package: "
					+ " An AWT based Console is available but not built by default.");
			// AWTConsole.main( args );
		}
	}

	public static void main2(String[] args) {
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

		inputLoop(console, "JCE (type 'quit' to exit): ", fts);

		System.exit(0);
	}
	
	/**
	 * Print prompt and echos commands entered via the JConsole
	 * 
	 * @param console
	 *            a GUIConsoleInterface which in addition to basic input and
	 *            output also provides coloured text output and name completion
	 * @param prompt
	 *            text to display before each input line
	 */
	private static void inputLoop(JConsole console, String prompt, Font[] fts) {
		//Reader input = console.getIn();
		Reader input = new InputStreamReader(console.getInputStream(),Charset.forName("iso-8859-1"));
		
		BufferedReader bufInput = new BufferedReader(input);

		String newline = System.getProperty("line.separator");

		console.print(prompt, Color.BLUE);

		String line;
		try {
			while ((line = bufInput.readLine()) != null) {
				//for (int i = 0; i < fts.length; i++) {
					line = StringUtil.unescapeJavaString(line);
					console.print("\tYou typed:"+line.length()+":" + line + newline, Color.ORANGE);
				//}

				// try to sync up the console
				// System.out.flush();
				// System.err.flush();
				// Thread.yield(); // this helps a little

				if (line.equals("quit"))
					break;
				console.print(prompt, Color.BLUE);
			}
			bufInput.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
