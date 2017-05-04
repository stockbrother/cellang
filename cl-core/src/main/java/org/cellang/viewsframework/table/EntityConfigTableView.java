package org.cellang.viewsframework.table;

import java.util.List;

import org.cellang.core.entity.EntityConfig;
import org.cellang.viewsframework.View;
import org.cellang.viewsframework.control.Action;
import org.cellang.viewsframework.control.HasActions;
import org.cellang.viewsframework.ops.OperationContext;

public class EntityConfigTableView extends TableDataView<EntityConfig> implements HasActions {
	OperationContext oc;
	EntityConfig selected;

	public EntityConfigTableView(OperationContext oc, List<EntityConfig> list) {
		super("EntityConfigs", oc, new EntityConfigTableDataProvider(list));
		this.oc = oc;
	}

	@Override
	public List<Action> getActions(Object context, List<Action> al) {
		if (context == this) {

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
		}
		return al;
	}

	@Override
	protected void onRowSelected(Integer row, EntityConfig rowObj) {
		this.selected = rowObj;
	}

	public void openSelectedView() {
		if (this.selected == null) {
			return;
		}
		View view = this.oc.getEntityConfigManager().newEntityListView(this.selected);
		this.oc.getViewManager().addView(view, true);//
	}

}
