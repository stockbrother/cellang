package org.cellang.console.view;

import java.util.List;

import org.cellang.console.control.Action;
import org.cellang.console.control.HasActions;
import org.cellang.console.model.EntityConfigTableDataProvider;
import org.cellang.console.ops.OperationContext;
import org.cellang.core.entity.EntityConfig;

public class EntityConfigTableView extends TableDataView<EntityConfig>implements HasActions {
	OperationContext oc;

	public EntityConfigTableView(OperationContext oc, List<EntityConfig> list) {
		super("EntityConfigs", new EntityConfigTableDataProvider(list));
		this.oc = oc;
	}

	@Override
	public List<Action> getActions(List<Action> al) {
		al.add(new Action() {

			@Override
			public String getName() {

				return "Drill Down";
			}

			@Override
			public void perform() {
				openSelectedView();
			}

		});
		return al;
	}

	private void openSelectedView() {
		if (this.selected == null) {
			return;
		}
		View view = this.oc.getEntityConfigManager().newEntityListView(this.selected);
		this.oc.getViewManager().addView(view, true);//
	}

}
