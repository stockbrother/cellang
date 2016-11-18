package org.cellang.viewsframework.view;

import java.util.List;

import org.cellang.viewsframework.control.Action;
import org.cellang.viewsframework.control.HasActions;
import org.cellang.viewsframework.customized.CustomizedReportUpdater;
import org.cellang.viewsframework.ops.OperationContext;

public class HomeView extends AbstractView implements HasActions {
	OperationContext oc;

	public HomeView(OperationContext oc) {
		super("Home", oc);
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
