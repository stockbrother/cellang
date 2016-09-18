package org.cellang.console.view;

import java.util.List;

import org.cellang.console.control.Action;
import org.cellang.console.control.HasActions;
import org.cellang.console.customized.CustomizedReportUpdater;
import org.cellang.console.format.ReportItemLocators;
import org.cellang.console.ops.OperationContext;
import org.cellang.console.view.report.ReportTemplateTableView;
import org.cellang.core.entity.BalanceSheetReportEntity;

public class HomeView extends AbstractView implements HasActions {
	OperationContext oc;

	public HomeView(OperationContext oc) {
		super("Home");
		this.oc = oc;
	}

	@Override
	public List<Action> getActions(Object context, List<Action> al) {
		if (context == this) {

			al.add(new Action() {

				@Override
				public String getName() {

					return "Update Customized Report";
				}

				@Override
				public void perform() {
					updateCustomizedReport();
				}

			});
			al.add(new Action() {

				@Override
				public String getName() {

					return "Report Template";
				}

				@Override
				public void perform() {
					openIndexSummary();
				}

			});
			

		}
		return al;
	}
	
	public void openIndexSummary(){
		ReportItemLocators.Group template = ReportItemLocators.getInstance().get(BalanceSheetReportEntity.class);
		View v = new ReportTemplateTableView<BalanceSheetReportEntity>(oc, BalanceSheetReportEntity.class, template);
		oc.getViewManager().addView(1, v, true);
	}

	public void updateCustomizedReport() {
		new CustomizedReportUpdater(oc.getEntityService()).execute();
	}

}
