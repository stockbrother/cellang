package org.cellang.console;

import javax.swing.JTextPane;

public class ConsoleTexPane extends JTextPane {
	
	private BshConsole console;

	public ConsoleTexPane(BshConsole console) {
	
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
