package org.cellang.console.control;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JScrollPane;

public class ActionsPane extends JScrollPane {
	private static class ActionUI {
		public ActionUI(Action a, JButton bu) {
			this.action = a;
			this.button = bu;
		}

		private Action action;
		private JButton button;
	}

	Box panel;
	private Component tailGlue;

	private Map<String, ActionUI> uiMap = new HashMap<>();

	public ActionsPane() {
		this.panel = new Box(BoxLayout.Y_AXIS);
		this.panel.setPreferredSize(new Dimension(200, 300));
		this.setViewportView(this.panel);
		this.tailGlue = Box.createVerticalGlue();
		this.clear();
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

		this.addToBox(bu);

		this.updateUI();
	}

	public void addText(Map<String, Object> des) {
		TextArea ta = new TextArea();
		ta.setText(des.toString());
		this.addToBox(ta);
	}

	public void addToBox(Component comp) {
		this.panel.remove(this.tailGlue);
		this.panel.add(comp);
		this.panel.add(this.tailGlue);//
	}

	public void addFilter(FilterPane fp) {
		this.addToBox(fp);
	}

	public void clear() {
		this.panel.removeAll();
		this.panel.add(this.tailGlue);
	}

	public void addActions(HasActions has) {
		List<Action> aL = has.getActions(new ArrayList<>());
		for (Action a : aL) {
			ActionUI aui = this.uiMap.get(a.getId());

			if (aui != null) {
				// avoid duplicated.
				continue;
			}
			JButton bu = new JButton(a.getName());
			{
				bu.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						a.perform();
					}
				});
			}

			aui = new ActionUI(a, bu);
			this.addToBox(bu);
			uiMap.put(a.getId(), aui);

		}
		this.updateUI();
	}
}
