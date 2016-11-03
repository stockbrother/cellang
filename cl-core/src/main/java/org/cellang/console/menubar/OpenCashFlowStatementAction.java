package org.cellang.console.menubar;

import org.cellang.console.control.Action;
import org.cellang.console.corpgrouping.CorpListView;
import org.cellang.console.corpgrouping.IncomeStatementReportView;
import org.cellang.console.ops.OperationContext;
import org.cellang.console.view.View;
import org.cellang.core.entity.CorpInfoEntity;

public class OpenCashFlowStatementAction extends Action {
	OperationContext oc;
	CorpListView corpListView;

	OpenCashFlowStatementAction(OperationContext oc) {
		this.oc = oc;
	}

	@Override
	public String getName() {

		return "Income Statement";

	}

	public void setCorpListView(CorpListView v) {
		this.corpListView = v;
	}

	@Override
	public void perform() {
		CorpInfoEntity e = this.corpListView.getSelected();
		if (e == null) {
			return;
		}
		View v = new IncomeStatementReportView(oc, 10, e.getId());
		oc.getViewManager().addView(1, v, true);
	}
}