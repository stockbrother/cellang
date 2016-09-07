package org.cellang.console.view;

import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTabbedPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ViewGroupPanel extends JTabbedPane {

	private static final Logger LOG = LoggerFactory.getLogger(ViewGroupPanel.class);

	List<ViewAddListener> llist = new ArrayList<ViewAddListener>();
	
	ViewGroupsPanel parent;
	
	public ViewGroupPanel(ViewGroupsPanel parent) {
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
		v.select(true);//
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
