package org.cellang.console.menubar;

import org.cellang.console.control.Action;
import org.cellang.console.ops.OperationContext;
import org.cellang.console.view.View;
import org.cellang.console.view.report.ReportTemplateTableView;
import org.cellang.core.entity.BalanceSheetReportEntity;

public class OpenBalanceSheetTemplateAction extends Action {
	OperationContext oc;

	OpenBalanceSheetTemplateAction(OperationContext oc) {
		this.oc = oc;
	}

	@Override
	public String getName() {

		return "Report Template(Balance)";

	}

	@Override
	public void perform() {

		View v = new ReportTemplateTableView(oc, BalanceSheetReportEntity.class);
		oc.getViewManager().addView(1, v, true);
	}
}