package org.cellang.console.control;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JScrollPane;

import org.cellang.console.view.View;

public class ViewActionPane extends JScrollPane {

	Map<String, ActionsPane> viewMap = new HashMap<>();

	public ViewActionPane() {

	}

	public void addActionPane(ActionsPane actionPane) {
		this.viewMap.put(actionPane.getViewId(), actionPane);
	}

	public void viewSelected(View v) {
		ActionsPane ap = this.viewMap.get(v.getId());
		this.setViewportView(ap);//
	}

}
