package org.cellang.console.view;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTabbedPane;

import org.cellang.console.EventBus;
import org.cellang.console.HasDelegateUtil;
import org.cellang.console.ops.OperationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ViewGroupPanel extends JTabbedPane {

	public static class ViewSelectionEvent {
		public View view;

		public View getView() {
			return view;
		}

		public ViewGroupPanel getGroup() {
			return group;
		}

		public ViewGroupPanel group;

		public ViewSelectionEvent(ViewGroupPanel g, View view) {
			this.group = g;
			this.view = view;
		}
	}

	public static class ViewRemoveEvent {
		public View view;

		public View getView() {
			return view;
		}

		public ViewGroupPanel getGroup() {
			return group;
		}

		public ViewGroupPanel group;

		public ViewRemoveEvent(ViewGroupPanel g, View view) {
			this.group = g;
			this.view = view;
		}
	}

	private static final Logger LOG = LoggerFactory.getLogger(ViewGroupPanel.class);

	List<ViewAddListener> llist = new ArrayList<ViewAddListener>();

	PerspectivePanel parent;
	EventBus eventBus;

	public ViewGroupPanel(OperationContext context, PerspectivePanel parent) {
		super.setUI(new ViewsTabbedPaneUI());
		this.eventBus = context.getEventBus();
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

	public List<View> getViewList() {
		List<View> rt = new ArrayList<>();
		for (int i = 0; i < this.getComponentCount(); i++) {
			View v = (View) this.getTabComponentAt(i);
			rt.add(v);
		}
		return rt;
	}

	public void closeCurrentView() {
		int idx = this.getSelectedIndex();
		if (idx == -1) {
			return;
		}
		View v = (View) this.getTabComponentAt(idx);
		this.remove(idx);
		this.viewRemoved(v);
	}

	public void clear() {
		List<View> vl = new ArrayList<View>();
		for (int i = 0; i < this.getTabCount(); i++) {
			View v = (View) this.getTabComponentAt(i);
			vl.add(v);
		}
		this.removeAll();
		for (View v : vl) {
			this.viewRemoved(v);
		}
		this.selectedChanged();

	}

	private void viewRemoved(View v) {
		this.eventBus.dispatch(new ViewRemoveEvent(this, v));
	}

	@Override
	public void remove(int index) {
		super.remove(index);
		this.selectedChanged();
	}

}
