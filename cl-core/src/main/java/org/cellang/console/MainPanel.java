package org.cellang.console;

import java.awt.GridLayout;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.cellang.console.ConsolePanel.ConsoleListener;

public class MainPanel extends JPanel {

	private ConsolePanel console;

	private OperationContext oc;

	private ViewManager views;
	int port = 7888;

	public MainPanel() {
		super(new GridLayout(2, 0));
		oc = new OperationContext();
	}

	public void start() {
		try {
			this.oc.start();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		JFrame frame = new JFrame("Tables");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setOpaque(true); // content panes must be opaque
		frame.setContentPane(this);

		views = oc.getViewManager();
		this.add(views);
		EntityConfigTableView table = new EntityConfigTableView(oc.getEntityConfigFactory().getEntityConfigList());
		views.addView(table);
		//
		console = new ConsolePanel(oc, port);
		this.add(console);
		frame.pack();
		console.addListener(new ConsoleListener() {

			@Override
			public void sessionCreated(ReplSession session) {
				// after the session created, the repl server is ready and the
				// client is ready too, so show the UI.
				//
				frame.setVisible(true);

			}
		});
		console.start();

	}

	public static void main(String[] args) {
		// Schedule a job for the event-dispatching thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				MainPanel mp = new MainPanel();
				mp.start();
			}
		});
	}

}
