package org.cellang.console;

import javax.swing.JTabbedPane;

public class ViewsPane extends JTabbedPane {

	public ViewsPane(OperationContext oc) {

	}

	public void addView(View v) {
		this.addTab(v.getTitle(), v.getComponent());
	}

	public void closeCurrentView() {
		int idx = this.getSelectedIndex();
		if (idx == -1) {

		}
		this.remove(idx);

	}

}
