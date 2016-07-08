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

import bsh.Capabilities;
import bsh.EvalError;
import bsh.Interpreter;
import bsh.util.JConsole;

public class Tmp2 {

	public static void maixn(String args[]) {

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

	public static void main(String[] args) {
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
	public static String unescapeJavaString(String st) {

	    StringBuilder sb = new StringBuilder(st.length());

	    for (int i = 0; i < st.length(); i++) {
	        char ch = st.charAt(i);
	        if (ch == '\\') {
	            char nextChar = (i == st.length() - 1) ? '\\' : st
	                    .charAt(i + 1);
	            // Octal escape?
	            if (nextChar >= '0' && nextChar <= '7') {
	                String code = "" + nextChar;
	                i++;
	                if ((i < st.length() - 1) && st.charAt(i + 1) >= '0'
	                        && st.charAt(i + 1) <= '7') {
	                    code += st.charAt(i + 1);
	                    i++;
	                    if ((i < st.length() - 1) && st.charAt(i + 1) >= '0'
	                            && st.charAt(i + 1) <= '7') {
	                        code += st.charAt(i + 1);
	                        i++;
	                    }
	                }
	                sb.append((char) Integer.parseInt(code, 8));
	                continue;
	            }
	            switch (nextChar) {
	            case '\\':
	                ch = '\\';
	                break;
	            case 'b':
	                ch = '\b';
	                break;
	            case 'f':
	                ch = '\f';
	                break;
	            case 'n':
	                ch = '\n';
	                break;
	            case 'r':
	                ch = '\r';
	                break;
	            case 't':
	                ch = '\t';
	                break;
	            case '\"':
	                ch = '\"';
	                break;
	            case '\'':
	                ch = '\'';
	                break;
	            // Hex Unicode: u????
	            case 'u':
	                if (i >= st.length() - 5) {
	                    ch = 'u';
	                    break;
	                }
	                int code = Integer.parseInt(
	                        "" + st.charAt(i + 2) + st.charAt(i + 3)
	                                + st.charAt(i + 4) + st.charAt(i + 5), 16);
	                sb.append(Character.toChars(code));
	                i += 5;
	                continue;
	            }
	            i++;
	        }
	        sb.append(ch);
	    }
	    return sb.toString();
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
					line = unescapeJavaString(line);
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
