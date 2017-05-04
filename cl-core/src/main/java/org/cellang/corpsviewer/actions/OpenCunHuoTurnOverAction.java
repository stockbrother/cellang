package org.cellang.corpsviewer.actions;

import org.cellang.viewsframework.View;
import org.cellang.viewsframework.control.Action;
import org.cellang.viewsframework.ops.OperationContext;
import org.cellang.viewsframework.view.list.CunHuoZhouZhuanListTableView;

public class OpenCunHuoTurnOverAction extends Action {
	OperationContext oc;

	public OpenCunHuoTurnOverAction(OperationContext oc) {
		this.oc = oc;
	}

	@Override
	public String getName() {

		return "存货周转天数";

	}

	@Override
	public void perform() {

		View v = new CunHuoZhouZhuanListTableView(oc);
		oc.getViewManager().addView(1, v, true);
	}
}