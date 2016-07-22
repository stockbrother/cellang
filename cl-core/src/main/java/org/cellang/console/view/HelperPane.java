package org.cellang.console.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;

import org.cellang.console.control.Action;
import org.cellang.console.control.ActionHandler;
import org.cellang.console.control.FilterPane;
import org.cellang.console.control.HasActions;
import org.cellang.console.control.ValueChangeListener;

public class HelperPane<T> extends JScrollPane {
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

	protected T contextObject;

	public HelperPane() {

	}

	public void setContextObject(T co) {
		this.contextObject = co;
		this.panel = new Box(BoxLayout.Y_AXIS);
		this.panel.setPreferredSize(new Dimension(100, 50));
		this.tailGlue = Box.createVerticalGlue();
		this.setViewportView(this.panel);

		if (co == null) {
			return;
		}
		if (co instanceof HasDelagates) {
			HasDelagates hs = (HasDelagates) co;
			HasActions has = hs.getDelegate(HasActions.class);
			if (has != null) {
				this.addActions(co, has);//
			}
		}
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

	public void addActions(Object context, HasActions has) {
		List<Action> aL = has.getActions(context, new ArrayList<>());
		for (Action a : aL) {
			// ActionUI aui = this.uiMap.get(a.getId());
			//
			// if (aui != null) {
			// // avoid duplicated.
			// continue;
			// }
			JButton bu = new JButton(a.getName());
			{
				bu.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						a.perform();
					}
				});
			}

			// aui = new ActionUI(a, bu);
			this.addToBox(bu);
			// uiMap.put(a.getId(), aui);

		}
		this.updateUI();
	}

	public void addDropDownList(List<String> nameL, ValueChangeListener<String> vcl) {
		Vector<String> vec = new Vector<String>(nameL);
		vec.insertElementAt(null, 0);//
		JComboBox<String> com = new JComboBox<>(vec);
		com.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent event) {
				if (event.getStateChange() == ItemEvent.SELECTED) {
					Object item = event.getItem();
					String name = (String) item;
					vcl.valueChanged(name);//
				}
			}
		});
		this.addToBox(com);

	}

}