package org.cellang.console.view.table;

import java.util.List;

import org.cellang.console.control.Action;
import org.cellang.console.control.HasActions;
import org.cellang.console.customized.CustomizedReportUpdater;
import org.cellang.console.ext.ExtendingPropertyUpdater;
import org.cellang.console.ops.OperationContext;
import org.cellang.core.entity.EntityConfig;

/**
 * @deprecated
 * @author wu
 *
 */
public class ExtendingPropertyMasterTableView extends TableDataView<ExtendingPropertyMasterTableDataProvider.RowObject>
		implements HasActions {
	OperationContext oc;
	ExtendingPropertyMasterTableDataProvider.RowObject selected;

	public ExtendingPropertyMasterTableView(OperationContext oc) {
		super("EntityConfigs", oc, new ExtendingPropertyMasterTableDataProvider(oc.getEntityService(),
				oc.getEntityConfigFactory(), oc.getEntityConfigManager()));
		this.oc = oc;
	}

	@Override
	protected void onRowSelected(Integer row, ExtendingPropertyMasterTableDataProvider.RowObject rowObj) {
		this.selected = rowObj;
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

					return "Update ExtendingProperties";
				}

				@Override
				public void perform() {
					updateExtendingProperty();
				}

			});

		}
		return al;
	}

	public void updateCustomizedReport() {
		new CustomizedReportUpdater(oc.getEntityService()).execute();
	}

	private void updateExtendingProperty() {
		if (this.selected == null) {
			return;
		}
		if (!this.selected.def.isSavable()) {
			return;
		}

		ExtendingPropertyUpdater up = new ExtendingPropertyUpdater(this.selected.def, oc.getEntityService());
		up.execute();

	}

}
