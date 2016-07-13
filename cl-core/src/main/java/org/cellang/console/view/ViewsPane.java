package org.cellang.console.view;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTabbedPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ViewsPane extends JTabbedPane {

	private static final Logger LOG = LoggerFactory.getLogger(ViewsPane.class);

	public static interface ViewsListener {

		public void viewAdded(View v);

		public void viewRemoved(View v);

		public void viewSelected(View v);
	}

	// TODO use property change mechanism for event processing.

	List<ViewsListener> llist = new ArrayList<ViewsListener>();

	public ViewsPane() {
		super.setUI(new ViewsTabbedPaneUI());
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
		for (ViewsListener l : llist) {
			l.viewSelected(v);//
		}
	}

	public void addViewsListener(ViewsListener vl) {
		this.llist.add(vl);
	}

	public void addView(View v, boolean select) {
		Component com = v.getComponent();
		this.addTab(v.getTitle() + "   ", com);
		for (ViewsListener l : llist) {
			l.viewAdded(v);
		}
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
		for (ViewsListener l : llist) {
			l.viewRemoved(v);
		}

	}

	public void clear() {
		List<View> vl = new ArrayList<View>();
		for (int i = 0; i < this.getTabCount(); i++) {
			View v = (View) this.getTabComponentAt(i);
			vl.add(v);
		}
		this.removeAll();
		for (View v : vl) {
			for (ViewsListener l : llist) {
				l.viewRemoved(v);
			}
		}
		this.selectedChanged();

	}

	@Override
	public void remove(int index) {
		super.remove(index);
		this.selectedChanged();
	}

}
