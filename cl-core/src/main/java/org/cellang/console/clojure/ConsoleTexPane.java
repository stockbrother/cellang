package org.cellang.console.clojure;

import javax.swing.JTextPane;

public class ConsoleTexPane extends JTextPane {
	
	private ConsolePane console;

	public ConsoleTexPane(ConsolePane console) {
	
	}

	public void cut() {
		if (this.getCaretPosition() < console.cmdStart) {
			super.copy();
		} else {
			super.cut();
		}
	}

	public void paste() {
		console.forceCaretMoveToEnd();
		super.paste();
	}
}
