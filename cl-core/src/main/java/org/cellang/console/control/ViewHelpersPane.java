package org.cellang.console.control;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JScrollPane;

import org.cellang.console.view.View;

public class ViewHelpersPane extends JScrollPane {

	Map<String, ViewHelperPane> viewMap = new HashMap<>();

	public ViewHelpersPane() {

	}

	public void addActionPane(ViewHelperPane actionPane) {
		this.viewMap.put(actionPane.getViewId(), actionPane);
	}

	public void viewSelected(View v) {
		ViewHelperPane ap = this.viewMap.get(v.getId());
		this.setViewportView(ap);//
	}

}
