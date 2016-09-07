package org.cellang.console;

import java.awt.BorderLayout;
import java.awt.Container;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.cellang.collector.EnvUtil;
import org.cellang.console.ops.OperationContext;
import org.cellang.console.view.ViewGroupsPanel;

public class Console {
	private static final boolean RIGHT_TO_LEFT = false;

	public static void addComponentsToPane(File dataDir, Container pane) {

		if (!(pane.getLayout() instanceof BorderLayout)) {
			pane.add(new JLabel("Container doesn't use BorderLayout!"));
			return;
		}

		if (RIGHT_TO_LEFT) {
			pane.setComponentOrientation(java.awt.ComponentOrientation.RIGHT_TO_LEFT);
		}

		JButton button = new JButton("Tool bar");
		button.setEnabled(false);
		pane.add(button, BorderLayout.PAGE_START);

		//HelpersPane helpersPane = new HelpersPane();
		//helpersPane.setPreferredSize(new Dimension(200, 100));
		
		ViewGroupsPanel views = new ViewGroupsPanel();
		//views.setPreferredSize(new Dimension(200, 100));
		pane.add(views, BorderLayout.CENTER);

		//button = new JButton("Button 3 (LINE_START)");
		//pane.add(button, BorderLayout.LINE_START);

		button = new JButton("Status bar.");
		button.setEnabled(false);
		pane.add(button, BorderLayout.PAGE_END);

		//pane.add(helpersPane, BorderLayout.LINE_END);

		OperationContext oc = new OperationContext(dataDir, views);
		//helpersPane.install(oc);
		oc.home();
	}

	private static void createAndShowGUI() {

		// Create and set up the window.
		JFrame frame = new JFrame("BorderLayoutDemo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Set up the content pane.
		File dataDir = EnvUtil.getDataDir();
		addComponentsToPane(dataDir, frame.getContentPane());
		// Use the content pane's default BorderLayout. No need for
		// setLayout(new BorderLayout());
		// Display the window.
		frame.pack();
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		/* Use an appropriate Look and Feel */
		try {
			// UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
		} catch (UnsupportedLookAndFeelException ex) {
			ex.printStackTrace();
		} catch (IllegalAccessException ex) {
			ex.printStackTrace();
		} catch (InstantiationException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
		/* Turn off metal's use bold fonts */
		UIManager.put("swing.boldMetal", Boolean.FALSE);

		// Schedule a job for the event dispatch thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}
}
