package org.cellang.corpsviewer.actions;

import org.cellang.core.entity.BalanceSheetReportEntity;
import org.cellang.viewsframework.View;
import org.cellang.viewsframework.control.Action;
import org.cellang.viewsframework.ops.OperationContext;
import org.cellang.viewsframework.view.report.ReportTemplateTableView;

public class OpenBalanceSheetTemplateAction extends Action {
	OperationContext oc;

	public OpenBalanceSheetTemplateAction(OperationContext oc) {
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