package org.cellang.console;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Vector;

import javax.swing.Icon;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

/**
 * A JFC/Swing based console for the BeanShell desktop. This is a descendant of
 * the old AWTConsole.
 * 
 * Improvements by: Mark Donszelmann <Mark.Donszelmann@cern.ch> including Cut &
 * Paste
 * 
 * Improvements by: Daniel Leuck including Color and Image support, key press
 * bug workaround
 */
public class BshConsole extends JScrollPane implements ActionListener, PropertyChangeListener {

	private static class ConsoleKeyListener implements KeyListener {
		BshConsole console;

		public ConsoleKeyListener(BshConsole console) {
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
					console.historyUp();
				}
				e.consume();
				break;

			case (KeyEvent.VK_DOWN):
				if (e.getID() == KeyEvent.KEY_PRESSED) {
					console.historyDown();
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
					console.histLine = 0;
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
					console.doCommandComplete(part);
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

	private static class ConsoleMouseListener implements MouseListener {

		private BshConsole console;

		public ConsoleMouseListener(BshConsole console) {
			this.console = console;
		}

		public void mouseClicked(MouseEvent event) {
		}

		public void mousePressed(MouseEvent event) {
			if (event.isPopupTrigger()) {
				console.menu.show((Component) event.getSource(), event.getX(), event.getY());
			}
		}

		public void mouseReleased(MouseEvent event) {
			if (event.isPopupTrigger()) {
				console.menu.show((Component) event.getSource(), event.getX(), event.getY());
			}
			console.text.repaint();
		}

		public void mouseEntered(MouseEvent event) {
		}

		public void mouseExited(MouseEvent event) {
		}
	}

	private final static String CUT = "Cut";
	private final static String COPY = "Copy";
	private final static String PASTE = "Paste";

	private OutputStream outPipe;
	private InputStream in;

	private int cmdStart = 0;
	private Vector<String> history = new Vector<String>();
	private String startedLine;
	private int histLine = 0;

	private JPopupMenu menu;
	protected JTextPane text;
	private DefaultStyledDocument doc;

	private NameComplete nameComplete;
	final int SHOW_AMBIG_MAX = 10;

	// hack to prevent key repeat for some reason?
	private boolean gotUp = true;

	public BshConsole() {
		ConsoleKeyListener kl = new ConsoleKeyListener(this);
		doc = new DefaultStyledDocument();
		text = new JTextPane(doc) {
			public void cut() {
				if (text.getCaretPosition() < cmdStart) {
					super.copy();
				} else {
					super.cut();
				}
			}

			public void paste() {
				forceCaretMoveToEnd();
				super.paste();
			}
		};

		Font font = new Font("Monospaced", Font.PLAIN, 14);
		text.setText("");
		text.setFont(font);
		text.setMargin(new Insets(7, 5, 7, 5));
		text.addKeyListener(kl);
		setViewportView(text);

		// create popup menu
		menu = new JPopupMenu("JConsole	Menu");
		menu.add(new JMenuItem(CUT)).addActionListener(this);
		menu.add(new JMenuItem(COPY)).addActionListener(this);
		menu.add(new JMenuItem(PASTE)).addActionListener(this);
		ConsoleMouseListener ml = new ConsoleMouseListener(this);
		text.addMouseListener(ml);

		// make sure popup menu follows Look & Feel
		UIManager.addPropertyChangeListener(this);

		outPipe = new PipedOutputStream();
		try {
			in = new PipedInputStream((PipedOutputStream) outPipe);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	public InputStream getInputStream() {
		return in;
	}

	private void doCommandComplete(String part) {
		if (nameComplete == null) {
			return;
		}

		int i = part.length() - 1;

		// Character.isJavaIdentifierPart() How convenient for us!!
		while (i >= 0 && (Character.isJavaIdentifierPart(part.charAt(i)) || part.charAt(i) == '.'))
			i--;

		part = part.substring(i + 1);

		if (part.length() < 2) // reasonable completion length
			return;

		// System.out.println("completing part: "+part);

		// no completion
		String[] complete = nameComplete.complete(part);
		if (complete.length == 0) {
			java.awt.Toolkit.getDefaultToolkit().beep();
			return;
		}

		// Found one completion (possibly what we already have)
		if (complete.length == 1 && !complete.equals(part)) {
			String append = complete[0].substring(part.length());
			append(append);
			return;
		}

		// Found ambiguous, show (some of) them

		String line = text.getText();
		String command = line.substring(cmdStart);
		// Find prompt
		for (i = cmdStart; line.charAt(i) != '\n' && i > 0; i--)
			;
		String prompt = line.substring(i + 1, cmdStart);

		// Show ambiguous
		StringBuffer sb = new StringBuffer("\n");
		for (i = 0; i < complete.length && i < SHOW_AMBIG_MAX; i++)
			sb.append(complete[i] + "\n");
		if (i == SHOW_AMBIG_MAX)
			sb.append("...\n");

		print(sb, Color.gray);
		print(prompt); // print resets command start
		append(command); // append does not reset command start
	}

	private void resetCommandStart() {
		cmdStart = textLength();
	}

	private void append(String string) {
		int slen = textLength();
		text.select(slen, slen);
		text.replaceSelection(string);
	}

	private String replaceRange(Object s, int start, int end) {
		String st = s.toString();
		text.select(start, end);
		text.replaceSelection(st);
		// text.repaint();
		return st;
	}

	private void forceCaretMoveToEnd() {
		if (text.getCaretPosition() < cmdStart) {
			// move caret first!
			text.setCaretPosition(textLength());
		}
		text.repaint();
	}

	private void forceCaretMoveToStart() {
		if (text.getCaretPosition() < cmdStart) {
			// move caret first!
		}
		text.repaint();
	}

	private void enter() {
		String s = getCmd();

		if (s.length() == 0) // special hack for empty return!
			s = ";\n";
		else {
			history.addElement(s);
			s = s + "\n";
		}

		append("\n");
		histLine = 0;
		acceptLine(s);
		text.repaint();
	}

	private String getCmd() {
		String s = "";
		try {
			s = text.getText(cmdStart, textLength() - cmdStart);
		} catch (BadLocationException e) {
			// should not happen
			System.out.println("Internal JConsole Error: " + e);
		}
		return s;
	}

	private void historyUp() {
		if (history.size() == 0) {
			return;
		}
		if (histLine == 0) {// save current line
			startedLine = getCmd();
		}

		if (histLine < history.size()) {
			histLine++;
			showHistoryLine();
		}
	}

	private void historyDown() {
		if (histLine == 0)
			return;

		histLine--;
		showHistoryLine();
	}

	private void showHistoryLine() {
		String showline;
		if (histLine == 0)
			showline = startedLine;
		else
			showline = (String) history.elementAt(history.size() - histLine);

		replaceRange(showline, cmdStart, textLength());
		text.setCaretPosition(textLength());
		text.repaint();
	}

	private static String ZEROS = "000";

	private void acceptLine(String line) {
		// Patch to handle Unicode characters
		// Submitted by Daniel Leuck
		StringBuffer buf = new StringBuffer();
		int lineLength = line.length();
		for (int i = 0; i < lineLength; i++) {
			char c = line.charAt(i);
			if (c > 127) {
				String val = Integer.toString(c, 16);
				val = ZEROS.substring(0, 4 - val.length()) + val;
				buf.append("\\u" + val);
			} else {
				buf.append(c);
			}
		}
		line = buf.toString();
		// End unicode patch

		try {
			outPipe.write(line.getBytes());
			outPipe.flush();
		} catch (IOException e) {
			outPipe = null;
			throw new RuntimeException("Console pipe broken...");
		}
		// text.repaint();
	}

	public void println(Object o) {
		print(String.valueOf(o) + "\n");
		text.repaint();
	}

	public void print(final Object o) {
		invokeAndWait(new Runnable() {
			public void run() {
				append(String.valueOf(o));
				resetCommandStart();
				text.setCaretPosition(cmdStart);
			}
		});
	}

	/**
	 * Prints "\\n" (i.e. newline)
	 */
	public void println() {
		print("\n");
		text.repaint();
	}

	public void error(Object o) {
		print(o, Color.red);
	}

	public void println(Icon icon) {
		print(icon);
		println();
		text.repaint();
	}

	public void print(final Icon icon) {
		if (icon == null)
			return;

		invokeAndWait(new Runnable() {
			public void run() {
				text.insertIcon(icon);
				resetCommandStart();
				text.setCaretPosition(cmdStart);
			}
		});
	}

	public void print(Object s, Font font) {
		print(s, font, null);
	}

	public void print(Object s, Color color) {
		print(s, null, color);
	}

	public void print(final Object o, final Font font, final Color color) {
		invokeAndWait(new Runnable() {
			public void run() {
				AttributeSet old = getStyle();
				setStyle(font, color);
				append(String.valueOf(o));
				resetCommandStart();
				text.setCaretPosition(cmdStart);
				setStyle(old, true);
			}
		});
	}

	public void print(Object s, String fontFamilyName, int size, Color color) {

		print(s, fontFamilyName, size, color, false, false, false);
	}

	public void print(final Object o, final String fontFamilyName, final int size, final Color color,
			final boolean bold, final boolean italic, final boolean underline) {
		invokeAndWait(new Runnable() {
			public void run() {
				AttributeSet old = getStyle();
				setStyle(fontFamilyName, size, color, bold, italic, underline);
				append(String.valueOf(o));
				resetCommandStart();
				text.setCaretPosition(cmdStart);
				setStyle(old, true);
			}
		});
	}

	private AttributeSet setStyle(Font font, Color color) {
		if (font != null)
			return setStyle(font.getFamily(), font.getSize(), color, font.isBold(), font.isItalic(),
					StyleConstants.isUnderline(getStyle()));
		else
			return setStyle(null, -1, color);
	}

	private AttributeSet setStyle(String fontFamilyName, int size, Color color) {
		MutableAttributeSet attr = new SimpleAttributeSet();
		if (color != null)
			StyleConstants.setForeground(attr, color);
		if (fontFamilyName != null)
			StyleConstants.setFontFamily(attr, fontFamilyName);
		if (size != -1)
			StyleConstants.setFontSize(attr, size);

		setStyle(attr);

		return getStyle();
	}

	private AttributeSet setStyle(String fontFamilyName, int size, Color color, boolean bold, boolean italic,
			boolean underline) {
		MutableAttributeSet attr = new SimpleAttributeSet();
		if (color != null)
			StyleConstants.setForeground(attr, color);
		if (fontFamilyName != null)
			StyleConstants.setFontFamily(attr, fontFamilyName);
		if (size != -1)
			StyleConstants.setFontSize(attr, size);
		StyleConstants.setBold(attr, bold);
		StyleConstants.setItalic(attr, italic);
		StyleConstants.setUnderline(attr, underline);

		setStyle(attr);

		return getStyle();
	}

	private void setStyle(AttributeSet attributes) {
		setStyle(attributes, false);
	}

	private void setStyle(AttributeSet attributes, boolean overWrite) {
		text.setCharacterAttributes(attributes, overWrite);
	}

	private AttributeSet getStyle() {
		return text.getCharacterAttributes();
	}

	public void setFont(Font font) {
		super.setFont(font);

		if (text != null)
			text.setFont(font);
	}

	// property change
	public void propertyChange(PropertyChangeEvent event) {
		if (event.getPropertyName().equals("lookAndFeel")) {
			SwingUtilities.updateComponentTreeUI(menu);
		}
	}

	// handle cut, copy and paste
	public void actionPerformed(ActionEvent event) {
		String cmd = event.getActionCommand();
		if (cmd.equals(CUT)) {
			text.cut();
		} else if (cmd.equals(COPY)) {
			text.copy();
		} else if (cmd.equals(PASTE)) {
			text.paste();
		}
	}

	/**
	 * If not in the event thread run via SwingUtilities.invokeAndWait()
	 */
	private void invokeAndWait(Runnable run) {
		if (!SwingUtilities.isEventDispatchThread()) {
			try {
				SwingUtilities.invokeAndWait(run);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		} else {
			run.run();
		}
	}

	public void setWaitFeedback(boolean on) {
		if (on)
			setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		else
			setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	}

	private int textLength() {
		return text.getDocument().getLength();
	}

}
