package org.cellang.console;

import javax.swing.JTabbedPane;

public class ViewManager extends JTabbedPane {

	public ViewManager(OperationContext oc) {

	}

	public void addView(View v) {
		this.addTab(v.getTitle(), v.getComponent());
	}

}
