package org.cellang.console;

import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;

import javax.swing.JFrame;

import bsh.util.JConsole;

public class CellangConsole {
	private JFrame frame;
	private JConsole console;
	private Reader input;

	public CellangConsole() {
		// define a frame and add a console to it
		frame = new JFrame("JConsole example");

		console = new JConsole();

		// Font font = Font.decode("YouYuan");
		// this.println("font:" + font);//
		// console.setFont(font);
		// console.print
		frame.getContentPane().add(console);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600, 400);

		frame.setVisible(true);
		input = new InputStreamReader(console.getInputStream(), Charset.forName("iso-8859-1"));

	}

	public Reader getInput() {
		return input;
	}

	public void println(Object obj) {
		this.console.print(obj);
		this.console.println();
	}

	public void print(Object obj) {
		this.console.print(obj);
	}
}
