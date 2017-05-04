package org.cellang.viewsframework.ops;

import org.cellang.viewsframework.View;

public class SelectedObjectContext {

	private Object selectedObject;

	private View selectedView;

	public View getSelectedView() {
		return selectedView;
	}

	public void setSelectedView(View selectedView) {
		this.selectedView = selectedView;
	}

	public Object getSelectedObject() {
		return selectedObject;
	}

	public void setSelectedObject(Object selectedObject) {
		this.selectedObject = selectedObject;
	}

}
