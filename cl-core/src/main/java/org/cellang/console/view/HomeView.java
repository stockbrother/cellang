package org.cellang.console.view;

import java.util.List;

import org.cellang.console.control.Action;
import org.cellang.console.control.HasActions;
import org.cellang.console.customized.CustomizedReportUpdater;
import org.cellang.console.ops.OperationContext;

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

		}
		return al;
	}

	public void updateCustomizedReport() {
		new CustomizedReportUpdater(oc.getEntityService()).execute();
	}

}
