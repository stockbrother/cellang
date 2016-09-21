package org.cellang.console.menubar;

import org.cellang.console.control.Action;
import org.cellang.console.corpgrouping.CorpGroupListView;
import org.cellang.console.ops.OperationContext;

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
