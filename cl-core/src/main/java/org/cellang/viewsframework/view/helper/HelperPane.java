package org.cellang.viewsframework.view.helper;

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
import javax.swing.JLabel;

import org.cellang.viewsframework.AbstractView;
import org.cellang.viewsframework.HasDelegates;
import org.cellang.viewsframework.control.Action;
import org.cellang.viewsframework.control.HasActions;
import org.cellang.viewsframework.control.ValueChangeListener;
import org.cellang.viewsframework.ops.OperationContext;

public class HelperPane<T> extends AbstractView {
	private static class ActionUI {
		public ActionUI(Action a, JButton bu) {
			this.action = a;
			this.button = bu;
		}

		private Action action;
		private JButton button;
	}

	Box panel;
	Box childActions;
	private Component tailGlue;

	private Map<String, ActionUI> uiMap = new HashMap<>();

	protected T contextObject;

	public HelperPane(String title, OperationContext oc) {
		super(title, oc);
	}

	public void setContextObject(T co) {
		this.contextObject = co;
		this.panel = new Box(BoxLayout.Y_AXIS);
		this.panel.setPreferredSize(new Dimension(100, 50));
		this.childActions = null;

		this.tailGlue = Box.createVerticalGlue();
		this.setViewportView(this.panel);

		if (co == null) {
			return;
		}
		if (co instanceof HasDelegates) {
			HasDelegates hs = (HasDelegates) co;
			HasActions has = hs.getDelegate(HasActions.class);
			if (has != null) {
				this.addActions(co, has);//
			}
		}
	}

	public void addAction(Action a) {
		JButton bu = new JButton(a.getName());
		{
			bu.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					a.perform();
				}
			});
		}
		if (this.childActions == null) {
			this.childActions = Box.createHorizontalBox();
			this.childActions.add(Box.createHorizontalGlue());
			this.addToBox(this.childActions);
		}

		// this.childActions.add(bu, this.childActions.getComponentCount() -
		// 1);//
		this.childActions.add(bu);//
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
			this.addAction(a);

		}
		this.updateUI();
	}

	public void addDropDownList(String title, List<String> nameL, ValueChangeListener<String> vcl) {
		Vector<String> vec = new Vector<String>(nameL);
		vec.insertElementAt(null, 0);//
		JComboBox<String> com = new JComboBox<>(vec);
		com.setPreferredSize(new Dimension(100, 20));//
		com.setMaximumSize(new Dimension(100, 20));//
		com.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent event) {
				if (event.getStateChange() == ItemEvent.SELECTED) {
					Object item = event.getItem();
					String name = (String) item;
					vcl.valueChanged(name);//
				}
			}
		});
		Box line = Box.createHorizontalBox();
		line.add(Box.createHorizontalGlue());
		JLabel label = new JLabel(title);
		label.setMinimumSize(new Dimension(50, 20));
		line.add(label);
		line.add(com);

		this.addToBox(line);

	}

}