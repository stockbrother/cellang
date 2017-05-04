package org.cellang.corpsviewer.myfavorites;

import org.cellang.corpsviewer.CashFlowReportView;
import org.cellang.viewsframework.View;
import org.cellang.viewsframework.ops.OperationContext;

public class OpenIncomeStatementAction extends AbstractFavoriteSelectedCorpAction {
	
	public OpenIncomeStatementAction(OperationContext oc) {
		super(oc);
	}

	@Override
	public String getName() {

		return "Cash Flow Statement";

	}

	@Override
	public void perform(String sid) {		
		View v = new CashFlowReportView(oc, 10, sid);
		oc.getViewManager().addView(1, v, true);
	}
}