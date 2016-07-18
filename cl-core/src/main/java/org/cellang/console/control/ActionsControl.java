package org.cellang.console.control;

import java.util.HashMap;
import java.util.Map;

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
	ActionsPane actions;
	EntityConfigManager entityConfigManager;

	public ActionsControl(EntityConfigManager ecm, ViewsPane views, ActionsPane actions) {
		this.entityConfigManager = ecm;
		this.views = views;
		this.actions = actions;
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
		actions.clear();
		if (v == null) {

		}
		if (v != null) {

			// if the view support query
			DataPageQuerable dpq = v.getDelegate(DataPageQuerable.class);
			if (dpq != null) {
				actions.addAction("prePage", new ActionHandler() {

					@Override
					public void performAction() {
						dpq.prePage();
					}
				});
				actions.addAction("refresh", new ActionHandler() {

					@Override
					public void performAction() {
						dpq.refresh();
					}
				});

				actions.addAction("nextPage", new ActionHandler() {

					@Override
					public void performAction() {
						dpq.nextPage();
					}
				});
			}
			// if the view is entity config list
			DrillDowable dd = v.getDelegate(DrillDowable.class);
			if (dd != null) {
				actions.addAction("drillDown", new ActionHandler() {

					@Override
					public void performAction() {
						dd.drillDown();
					}
				});
			}
			// if the view contains description
			Descriable des = v.getDelegate(Descriable.class);
			if (des != null) {
				Map<String, Object> desMap = new HashMap<>();
				des.getDescription(desMap);
				actions.addText(desMap);

			}
			// if the view support fitler
			Filterable fil = v.getDelegate(Filterable.class);
			if (fil != null) {

				actions.addFilter(new FilterPane(fil));
			}

			// if the view is entity list
			HasActions has = v.getDelegate(HasActions.class);
			if (has != null) {
				actions.addActions(has);
			}
		}
		actions.updateUI();

	}

	@Override
	public void onEntityConfigSelected(EntityConfig selected) {
		EntityConfigControl<?> ecc = this.entityConfigManager.getEntityConfigControl(selected);
		if (ecc == null) {
			return;
		}
		HasActions has = ecc.getDelegate(HasActions.class);

		if (has != null) {
			actions.addActions(has);
		}
	}

}
