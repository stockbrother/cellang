package org.cellang.console.menubar;

import org.cellang.console.control.Action;
import org.cellang.console.corpgrouping.CorpGroupEditView;
import org.cellang.console.ops.OperationContext;

public class OpenAddingCorpGroupViewAction extends Action {
	OperationContext oc;

	public OpenAddingCorpGroupViewAction(OperationContext oc) {
		this.oc = oc;
	}

	@Override
	public String getName() {
		return "New Corp Group ";
	}

	@Override
	public void perform() {
		CorpGroupEditView view = new CorpGroupEditView(oc);
		oc.getViewManager().addView(view, true);		
	}

}
