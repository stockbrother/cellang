package org.cellang.console.view;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTabbedPane;

import org.cellang.console.EventBus;
import org.cellang.console.HasDelagateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ViewGroupPanel extends JTabbedPane {

	public static class ViewSelectionEvent {
		public View view;
		public ViewGroupPanel group;

		public ViewSelectionEvent(ViewGroupPanel g, View view) {
			this.group = g;
			this.view = view;
		}
	}

	private static final Logger LOG = LoggerFactory.getLogger(ViewGroupPanel.class);

	List<ViewAddListener> llist = new ArrayList<ViewAddListener>();

	PerspectivePanel parent;
	EventBus eventBus;

	public ViewGroupPanel(Object context, PerspectivePanel parent) {
		super.setUI(new ViewsTabbedPaneUI());
		this.eventBus = HasDelagateUtil.getDelagate(context, EventBus.class, true);
	}

	@Override
	public void setSelectedIndex(int idx) {
		super.setSelectedIndex(idx);
		if (LOG.isDebugEnabled()) {
			LOG.debug("setSelected:" + idx);//
		}
		this.selectedChanged();
	}

	private void selectedChanged() {
		View v = (View) this.getSelectedComponent();
		this.eventBus.dispatch(new ViewSelectionEvent(this, v));
	}

	public void addViewsListener(ViewAddListener vl) {
		this.llist.add(vl);
	}

	public void addView(View v, boolean select) {
		Component com = v.getComponent();
		this.addTab(v.getTitle() + "   ", com);

		if (select) {
			this.setSelectedComponent(com);
		}
	}

	public void closeCurrentView() {
		int idx = this.getSelectedIndex();
		if (idx == -1) {
			return;
		}
		View v = (View) this.getTabComponentAt(idx);
		this.remove(idx);
		parent.viewRemoved(v);
	}

	public void clear() {
		List<View> vl = new ArrayList<View>();
		for (int i = 0; i < this.getTabCount(); i++) {
			View v = (View) this.getTabComponentAt(i);
			vl.add(v);
		}
		this.removeAll();
		for (View v : vl) {
			parent.viewRemoved(v);
		}
		this.selectedChanged();

	}

	@Override
	public void remove(int index) {
		super.remove(index);
		this.selectedChanged();
	}

}
