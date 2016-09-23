package org.cellang.console.menubar;

import org.cellang.console.control.Action;
import org.cellang.console.corpgrouping.CorpListView;
import org.cellang.console.ops.OperationContext;

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
