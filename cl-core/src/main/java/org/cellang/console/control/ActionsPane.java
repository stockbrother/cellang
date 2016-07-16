package org.cellang.console.control;

import java.awt.Dimension;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class ActionsPane extends JScrollPane {
	JPanel panel;

	public ActionsPane() {
		this.panel = new JPanel();
		this.panel.setPreferredSize(new Dimension(500, 300));
		this.setViewportView(this.panel);
	}

	public void addAction(String name, ActionHandler ah) {
		JButton bu = new JButton(name);
		{
			bu.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					ah.performAction();
				}
			});
		}
		this.panel.add(bu);
		this.updateUI();
	}

	public void addText(Map<String, Object> des) {
		TextArea ta = new TextArea();
		ta.setText(des.toString());
		this.panel.add(ta);
	}

	public void clear() {
		this.panel.removeAll();
	}
}
