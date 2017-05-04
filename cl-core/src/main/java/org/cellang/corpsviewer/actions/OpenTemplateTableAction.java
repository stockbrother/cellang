package org.cellang.corpsviewer.actions;

import org.cellang.core.entity.CustomizedReportEntity;
import org.cellang.viewsframework.View;
import org.cellang.viewsframework.control.Action;
import org.cellang.viewsframework.ops.OperationContext;
import org.cellang.viewsframework.view.report.ReportTemplateTableView;

public class OpenTemplateTableAction extends Action {
	OperationContext oc;

	public OpenTemplateTableAction(OperationContext oc) {
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