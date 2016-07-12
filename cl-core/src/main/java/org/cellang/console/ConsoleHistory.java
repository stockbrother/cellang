package org.cellang.console;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.Vector;

public class ConsoleHistory extends Vector<String> {
	int histLine = 0;
	BshConsole console;
	private int maxHistory = 1000;
	File dataDir;
	File historyFile;

	public ConsoleHistory(File dataDir, BshConsole console) {
		this.dataDir = dataDir;
		this.console = console;
		historyFile = new File(this.dataDir, "history");
	}

	public void load() {

		if (!historyFile.exists()) {
			return;
		}
		BufferedReader rd;
		try {
			rd = new BufferedReader(
					new InputStreamReader(new FileInputStream(this.historyFile), Charset.forName("utf-8")));
			while (true) {
				String line = rd.readLine();
				if (line == null) {
					break;
				}
				this.addElement(line);
			}
			rd.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	public void save() {
		try {
			Writer wt = new OutputStreamWriter(new FileOutputStream(this.historyFile), Charset.forName("utf-8"));
			for (int i = 0; i < this.size(); i++) {
				String line = this.elementAt(i);
				wt.write(line);
				wt.write("\r\n");//
			}
			wt.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	void historyUp() {
		if (this.size() == 0) {
			return;
		}
		if (this.histLine == 0) {// save current line
			console.startedLine = console.getCmd();
		}

		if (this.histLine < this.size()) {
			this.histLine++;
			showHistoryLine();
		}
	}

	void historyDown() {
		if (histLine == 0) {
			return;
		}

		histLine--;
		showHistoryLine();
	}

	private void showHistoryLine() {
		String showline;
		if (histLine == 0) {

			showline = console.startedLine;
		} else {
			showline = (String) elementAt(size() - histLine);
		}

		console.replaceRange(showline, console.cmdStart, console.textLength());
		console.text.setCaretPosition(console.textLength());
		console.text.repaint();
	}
}
