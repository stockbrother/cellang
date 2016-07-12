package org.cellang.console;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JTabbedPane;

public class ViewsPane extends JTabbedPane {
	public static interface ViewsListener {

		public void viewAdded(View v);

		public void viewRemoved(View v);
		
		public void viewSelected(View v);
	}

	List<ViewsListener> llist = new ArrayList<ViewsListener>();

	public ViewsPane() {
		super.setUI(new ViewsTabbedPaneUI());
	}
	
	public void addViewsListener(ViewsListener vl){
		this.llist.add(vl);
	}
	
	public void addView(View v) {
		this.addTab(v.getTitle() + "  ", v.getComponent());
		for (ViewsListener l : llist) {
			l.viewAdded(v);
		}

	}

	public void closeCurrentView() {
		int idx = this.getSelectedIndex();
		if (idx == -1) {

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

	}

}
