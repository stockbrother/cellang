package org.cellang.console.menubar;

import org.cellang.console.control.Action;
import org.cellang.console.ops.OperationContext;
import org.cellang.console.view.View;
import org.cellang.console.view.report.ReportTemplateTableView;
import org.cellang.core.entity.CustomizedReportEntity;

public class OpenTemplateTableAction extends Action {
	OperationContext oc;

	OpenTemplateTableAction(OperationContext oc) {
		this.oc = oc;
	}

	@Override
	public String getName() {

		return "Report Template(Other)";

	}

	@Override
	public void perform() {
		View v = new ReportTemplateTableView(oc, CustomizedReportEntity.class);
		oc.getViewManager().addView(1, v, true);
	}
}