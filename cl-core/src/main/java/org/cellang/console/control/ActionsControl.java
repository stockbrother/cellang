package org.cellang.console.control;

import java.util.HashMap;
import java.util.Map;

import org.cellang.console.view.View;
import org.cellang.console.view.ViewsPane;
import org.cellang.console.view.ViewsPane.ViewsListener;
import org.cellang.core.entity.EntityObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ActionsControl implements ViewsListener, EntityObjectSourceListener {
	private static final Logger LOG = LoggerFactory.getLogger(ActionsControl.class);

	ViewsPane views;
	ActionsPane actions;

	public ActionsControl(ViewsPane views, ActionsPane actions) {

		this.views = views;
		this.actions = actions;
		this.views.addViewsListener(this);

	}

	@Override
	public void viewAdded(View v) {
		EntityObjectSource eos = v.getDelegate(EntityObjectSource.class);
		if (eos != null) {
			eos.addEntityObjectSourceListener(this);
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
			if(has != null){				
				actions.addActions(has);
			}
		}
		actions.updateUI();

	}

}
