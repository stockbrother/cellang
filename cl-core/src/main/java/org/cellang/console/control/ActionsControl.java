package org.cellang.console.control;

import java.util.List;

import org.cellang.console.ops.EntityConfigManager;
import org.cellang.console.view.View;
import org.cellang.console.view.ViewsPane;
import org.cellang.console.view.ViewsPane.ViewsListener;
import org.cellang.core.entity.EntityConfig;
import org.cellang.core.entity.EntityObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ActionsControl implements ViewsListener, EntityObjectSelectionListener, EntityConfigSelectionListener {
	private static final Logger LOG = LoggerFactory.getLogger(ActionsControl.class);

	ViewsPane views;

	ViewActionPane actionManagerPane;

	EntityConfigManager entityConfigManager;

	public ActionsControl(EntityConfigManager ecm, ViewsPane views, ViewActionPane actions) {
		this.entityConfigManager = ecm;
		this.views = views;
		this.actionManagerPane = actions;
		this.views.addViewsListener(this);
		this.entityConfigManager.addEntityConfigSelectionListener(this);//

	}

	@Override
	public void viewAdded(View v) {
		EntityObjectSelector eos = v.getDelegate(EntityObjectSelector.class);
		if (eos != null) {
			eos.addEntityObjectSelectionListener(this);
		}
		EntityConfigSelector ecs = v.getDelegate(EntityConfigSelector.class);
		if (ecs != null) {
			ecs.addEntityConfigSelectionListener(this);
		}

		ActionsPane actionPane = new ActionsPane(v);
		actionManagerPane.addActionPane(actionPane);

		ColumnAppendable ce = v.getDelegate(ColumnAppendable.class);
		if (ce != null) {
			List<String> nameL = ce.getExtenableColumnList();
			actionPane.addDropDownList(nameL, new ValueChangeListener<String>() {

				@Override
				public void valueChanged(String value) {
					ce.appendColumn(value);//
				}
			});
		}

		return;
	}

	@Override
	public void viewRemoved(View v) {

	}

	@Override
	public void onEntitySelected(EntityObject eo) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("entitySelected:" + eo);
		}
		if (eo == null) {
			return;
		}
		Class<?> ecls = eo.getClass();
		EntityConfigControl<?> ecc = this.entityConfigManager.getEntityConfigControl(ecls);
		EntityObjectSelectionListener sl = ecc.getDelegate(EntityObjectSelectionListener.class);
		if (sl == null) {
			return;
		}
		sl.onEntitySelected(eo);//

	}

	@Override
	public void viewSelected(View v) {
		actionManagerPane.viewSelected(v);
	}

	@Override
	public void onEntityConfigSelected(EntityConfig selected) {
		EntityConfigControl<?> ecc = this.entityConfigManager.getEntityConfigControl(selected);
		if (ecc == null) {
			return;
		}
		HasActions has = ecc.getDelegate(HasActions.class);

	}

}
