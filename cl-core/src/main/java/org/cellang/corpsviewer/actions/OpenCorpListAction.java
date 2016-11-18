package org.cellang.corpsviewer.actions;

import org.cellang.corpsviewer.CorpListView;
import org.cellang.viewsframework.control.Action;
import org.cellang.viewsframework.ops.OperationContext;

public class OpenCorpListAction extends Action {
	OperationContext oc;

	public OpenCorpListAction(OperationContext oc) {
		this.oc = oc;
	}

	@Override
	public String getName() {
		return "Corp List";
	}

	@Override
	public void perform() {
		CorpListView view = new CorpListView(oc);
		oc.getViewManager().addView(view, true);
	}

}
