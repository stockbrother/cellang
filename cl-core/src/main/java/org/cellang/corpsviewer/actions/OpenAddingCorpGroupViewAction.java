package org.cellang.corpsviewer.actions;

import org.cellang.viewsframework.control.Action;
import org.cellang.viewsframework.corpgrouping.CorpGroupEditView;
import org.cellang.viewsframework.ops.OperationContext;

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
