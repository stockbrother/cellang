package org.cellang.corpsviewer.actions;

import org.cellang.corpsviewer.CorpGroupListView;
import org.cellang.viewsframework.control.Action;
import org.cellang.viewsframework.ops.OperationContext;

public class OpenCorpGroupAction extends Action {
	OperationContext oc;

	public OpenCorpGroupAction(OperationContext oc) {
		this.oc = oc;
	}

	@Override
	public String getName() {
		return "Corp Group List";
	}

	@Override
	public void perform() {
		CorpGroupListView view = new CorpGroupListView(oc);
		oc.getViewManager().addView(view, true);		
	}

}
