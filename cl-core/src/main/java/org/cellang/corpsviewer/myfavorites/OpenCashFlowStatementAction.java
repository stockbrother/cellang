package org.cellang.corpsviewer.myfavorites;

import org.cellang.corpsviewer.IncomeStatementReportView;
import org.cellang.viewsframework.ops.OperationContext;
import org.cellang.viewsframework.view.View;

public class OpenCashFlowStatementAction extends AbstractFavoriteSelectedCorpAction {

	public OpenCashFlowStatementAction(OperationContext oc) {
		super(oc);
	}

	@Override
	public String getName() {

		return "Income Statement";

	}

	@Override
	public void perform(String sid) {
		View v = new IncomeStatementReportView(oc, 10, sid);
		oc.getViewManager().addView(1, v, true);
	}
}