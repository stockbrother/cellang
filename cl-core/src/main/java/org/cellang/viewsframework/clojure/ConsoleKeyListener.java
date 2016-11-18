package org.cellang.viewsframework.clojure;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

class ConsoleKeyListener implements KeyListener {
	ConsolePane console;
	
	public ConsoleKeyListener(ConsolePane console) {
		this.console = console;
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		type(e);
		console.gotUp = false;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		type(e);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		console.gotUp = true;
		type(e);
	}

	private synchronized void type(KeyEvent e) {
		switch (e.getKeyCode()) {
		case (KeyEvent.VK_ENTER):
			if (e.getID() == KeyEvent.KEY_PRESSED) {
				if (console.gotUp) {
					console.enter();
					console.resetCommandStart();
					console.text.setCaretPosition(console.cmdStart);
				}
			}
			e.consume();
			console.text.repaint();
			break;

		case (KeyEvent.VK_UP):
			if (e.getID() == KeyEvent.KEY_PRESSED) {
				console.history.historyUp();
			}
			e.consume();
			break;

		case (KeyEvent.VK_DOWN):
			if (e.getID() == KeyEvent.KEY_PRESSED) {
				console.history.historyDown();
			}
			e.consume();
			break;

		case (KeyEvent.VK_LEFT):
		case (KeyEvent.VK_BACK_SPACE):
		case (KeyEvent.VK_DELETE):
			if (console.text.getCaretPosition() <= console.cmdStart) {
				// This doesn't work for backspace.
				// See default case for workaround
				e.consume();
			}
			break;

		case (KeyEvent.VK_RIGHT):
			console.forceCaretMoveToStart();
			break;

		case (KeyEvent.VK_HOME):
			console.text.setCaretPosition(console.cmdStart);
			e.consume();
			break;

		case (KeyEvent.VK_U): // clear line
			if ((e.getModifiers() & InputEvent.CTRL_MASK) > 0) {
				console.replaceRange("", console.cmdStart, console.textLength());
				console.history.histLine = 0;
				e.consume();
			}
			break;

		case (KeyEvent.VK_ALT):
		case (KeyEvent.VK_CAPS_LOCK):
		case (KeyEvent.VK_CONTROL):
		case (KeyEvent.VK_META):
		case (KeyEvent.VK_SHIFT):
		case (KeyEvent.VK_PRINTSCREEN):
		case (KeyEvent.VK_SCROLL_LOCK):
		case (KeyEvent.VK_PAUSE):
		case (KeyEvent.VK_INSERT):
		case (KeyEvent.VK_F1):
		case (KeyEvent.VK_F2):
		case (KeyEvent.VK_F3):
		case (KeyEvent.VK_F4):
		case (KeyEvent.VK_F5):
		case (KeyEvent.VK_F6):
		case (KeyEvent.VK_F7):
		case (KeyEvent.VK_F8):
		case (KeyEvent.VK_F9):
		case (KeyEvent.VK_F10):
		case (KeyEvent.VK_F11):
		case (KeyEvent.VK_F12):
		case (KeyEvent.VK_ESCAPE):

			// only modifier pressed
			break;

		// Control-C
		case (KeyEvent.VK_C):
			if (console.text.getSelectedText() == null) {
				if (((e.getModifiers() & InputEvent.CTRL_MASK) > 0) && (e.getID() == KeyEvent.KEY_PRESSED)) {
					console.append("^C");
				}
				e.consume();
			}
			break;

		case (KeyEvent.VK_TAB):
			if (e.getID() == KeyEvent.KEY_RELEASED) {
				String part = console.text.getText().substring(console.cmdStart);
				console.nameComplete.doCommandComplete(part);
			}
			e.consume();
			break;

		default:
			if ((e.getModifiers() & (InputEvent.CTRL_MASK | InputEvent.ALT_MASK | InputEvent.META_MASK)) == 0) {
				// plain character
				console.forceCaretMoveToEnd();
			}

			/*
			 * The getKeyCode function always returns VK_UNDEFINED for
			 * keyTyped events, so backspace is not fully consumed.
			 */
			if (e.paramString().indexOf("Backspace") != -1) {
				if (console.text.getCaretPosition() <= console.cmdStart) {
					e.consume();
					break;
				}
			}

			break;
		}
	}

}