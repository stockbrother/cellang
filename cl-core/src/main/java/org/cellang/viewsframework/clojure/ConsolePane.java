package org.cellang.viewsframework.clojure;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Insets;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

import javax.swing.Icon;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConsolePane extends JScrollPane implements PropertyChangeListener {

	private static Logger LOG = LoggerFactory.getLogger(ConsolePane.class);

	private static String ZEROS = "000";

	private PipedOutputStream outPipe;
	private InputStream in;

	int cmdStart = 0;
	ConsoleHistory history;
	String startedLine;

	JPopupMenu menu;
	protected JTextPane text;
	ConsoleNameComplete nameComplete;

	final int SHOW_AMBIG_MAX = 10;

	// hack to prevent key repeat for some reason?
	boolean gotUp = true;
	File dataDir;

	public ConsolePane(File dataDir) {
		this.dataDir = dataDir;
		this.history = new ConsoleHistory(dataDir, this);

		ConsoleKeyListener kl = new ConsoleKeyListener(this);
		this.nameComplete = new ConsoleNameComplete(this);
		text = new ConsoleTexPane(this);

		Font font = new Font("Monospaced", Font.PLAIN, 14);
		text.setText("");
		text.setFont(font);
		text.setMargin(new Insets(7, 5, 7, 5));
		text.addKeyListener(kl);
		setViewportView(text);

		// create popup menu
		menu = new ConsoleMenu(this);

		ConsoleMouseListener ml = new ConsoleMouseListener(this);
		text.addMouseListener(ml);

		// make sure popup menu follows Look & Feel
		UIManager.addPropertyChangeListener(this);

		outPipe = new PipedOutputStream();
		try {
			in = new PipedInputStream(outPipe);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	public InputStream getInputStream() {
		return in;
	}

	void resetCommandStart() {
		cmdStart = textLength();
	}

	void append(String string) {
		int slen = textLength();
		text.select(slen, slen);
		text.replaceSelection(string);
	}

	String replaceRange(Object s, int start, int end) {
		String st = s.toString();
		text.select(start, end);
		text.replaceSelection(st);
		// text.repaint();
		return st;
	}

	void forceCaretMoveToEnd() {
		if (text.getCaretPosition() < cmdStart) {
			// move caret first!
			text.setCaretPosition(textLength());
		}
		text.repaint();
	}

	void forceCaretMoveToStart() {
		if (text.getCaretPosition() < cmdStart) {
			// move caret first!
		}
		text.repaint();
	}

	void enter() {
		String s = getCmd();

		
		history.addElement(s);
		s = s + "\n";		
		append("\n");
		
		history.histLine = 0;
		acceptLine(s);
		text.repaint();
	}

	String getCmd() {
		String s = "";
		try {
			s = text.getText(cmdStart, textLength() - cmdStart);
		} catch (BadLocationException e) {
			throw new RuntimeException(e);
		}
		return s;
	}

	public void inputLine(String line) {
		this.acceptLine(line + "\r\n");//
	}

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
		if (font != null) {
			return setStyle(font.getFamily(), font.getSize(), color, font.isBold(), font.isItalic(),
					StyleConstants.isUnderline(getStyle()));
		} else {
			return setStyle(null, -1, color);
		}
	}

	private AttributeSet setStyle(String fontFamilyName, int size, Color color) {
		MutableAttributeSet attr = new SimpleAttributeSet();
		if (color != null) {
			StyleConstants.setForeground(attr, color);
		}
		if (fontFamilyName != null) {
			StyleConstants.setFontFamily(attr, fontFamilyName);
		}
		if (size != -1) {
			StyleConstants.setFontSize(attr, size);
		}

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

		if (text != null) {
			text.setFont(font);
		}
	}

	// property change
	public void propertyChange(PropertyChangeEvent event) {
		if (event.getPropertyName().equals("lookAndFeel")) {
			SwingUtilities.updateComponentTreeUI(menu);
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
		if (on) {
			setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		} else {
			setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
	}

	protected int textLength() {
		return text.getDocument().getLength();
	}

}
