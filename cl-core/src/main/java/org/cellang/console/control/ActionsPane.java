package org.cellang.console.control;

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

import org.cellang.console.view.View;

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
	private View view;

	public String getViewId() {
		return this.view.getId();
	}

	public ActionsPane(View view) {
		this.view = view;
		this.panel = new Box(BoxLayout.Y_AXIS);
		this.panel.setPreferredSize(new Dimension(200, 300));
		this.setViewportView(this.panel);

		this.tailGlue = Box.createVerticalGlue();
		this.clear();
		// if the view support query
		DataPageQuerable dpq = view.getDelegate(DataPageQuerable.class);
		if (dpq != null) {
			this.addAction("prePage", new ActionHandler() {

				@Override
				public void performAction() {
					dpq.prePage();
				}
			});
			this.addAction("refresh", new ActionHandler() {

				@Override
				public void performAction() {
					dpq.refresh();
				}
			});

			this.addAction("nextPage", new ActionHandler() {

				@Override
				public void performAction() {
					dpq.nextPage();
				}
			});
		}
		// if the view is entity config list
		DrillDowable dd = view.getDelegate(DrillDowable.class);
		if (dd != null) {
			this.addAction("drillDown", new ActionHandler() {

				@Override
				public void performAction() {
					dd.drillDown();
				}
			});
		}
		// if the view contains description
		Descriable des = view.getDelegate(Descriable.class);
		if (des != null) {
			Map<String, Object> desMap = new HashMap<>();
			des.getDescription(desMap);
			this.addText(desMap);// TODO

		}
		// if the view support fitler
		Filterable fil = view.getDelegate(Filterable.class);
		if (fil != null) {

			this.addFilter(new FilterPane(fil));
		}

		//
		HasActions has = view.getDelegate(HasActions.class);

		if (has != null) {
			this.addActions(has);
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

	public void addActions(HasActions has) {
		List<Action> aL = has.getActions(view, new ArrayList<>());
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
