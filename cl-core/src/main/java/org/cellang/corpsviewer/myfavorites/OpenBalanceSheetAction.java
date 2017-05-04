package org.cellang.corpsviewer.myfavorites;

import org.cellang.corpsviewer.BalanceSheetReportView;
import org.cellang.viewsframework.View;
import org.cellang.viewsframework.ops.OperationContext;

public class OpenBalanceSheetAction extends AbstractFavoriteSelectedCorpAction {

	public OpenBalanceSheetAction(OperationContext oc) {
		super(oc);
	}

	@Override
	public String getName() {

		return "Balance Sheet";

	}

	@Override
	protected void perform(String sid) {		
		
		View v = new BalanceSheetReportView(oc, 10, sid);
		oc.getViewManager().addView(1, v, true);
	}
}