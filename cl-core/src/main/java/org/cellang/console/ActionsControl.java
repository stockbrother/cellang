package org.cellang.console;

import org.cellang.console.ViewsPane.ViewsListener;
import org.cellang.core.entity.EntityObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ActionsControl implements ViewsListener, EntityObjectSourceListener {
	private static final Logger LOG = LoggerFactory.getLogger(ActionsControl.class);

	ViewsPane views;
	ActionsPane actions;
	EventBus ebus;

	public ActionsControl(EventBus ebus, ViewsPane views, ActionsPane actions) {
		this.ebus = ebus;
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

		DataPageQuerable dpq = v.getDelegate(DataPageQuerable.class);
		if (dpq != null) {
			actions.addAction("prePage", new ActionHandler() {

				@Override
				public void performAction() {
					dpq.prePage();
				}
			});

			actions.addAction("nextPage", new ActionHandler() {

				@Override
				public void performAction() {
					dpq.nextPage();
				}
			});
		}
	}

}
