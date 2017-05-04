package org.cellang.corpsviewer.myfavorites;

import org.cellang.corpsviewer.BalanceSheetQinSuanView;
import org.cellang.viewsframework.View;
import org.cellang.viewsframework.ops.OperationContext;

public class OpenBalanceSheetQinSuanAction extends AbstractFavoriteSelectedCorpAction {

	public OpenBalanceSheetQinSuanAction(OperationContext oc) {
		super(oc);
	}

	@Override
	public String getName() {

		return "Balance Sheet(Qin Suan)";

	}

	@Override
	protected void perform(String sid) {		
		
		View v = new BalanceSheetQinSuanView(oc, 10, sid);
		oc.getViewManager().addView(1, v, true);
	}
}