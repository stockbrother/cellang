package org.cellang.console.control;

import java.util.List;

import org.cellang.console.ops.EntityConfigManager;
import org.cellang.console.view.View;
import org.cellang.console.view.ViewsPane;
import org.cellang.console.view.ViewsPane.ViewsListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ActionsControl implements ViewsListener {
	private static final Logger LOG = LoggerFactory.getLogger(ActionsControl.class);

	ViewsPane views;

	ViewHelpersPane actionManagerPane;

	EntityConfigManager entityConfigManager;

	public ActionsControl(EntityConfigManager ecm, ViewsPane views, ViewHelpersPane actions) {
		this.entityConfigManager = ecm;
		this.views = views;
		this.actionManagerPane = actions;
		this.views.addViewsListener(this);
		

	}

	@Override
	public void viewAdded(View v) {
				
		ViewHelperPane actionPane = new ViewHelperPane(v);
		actionManagerPane.addActionPane(actionPane);

		return;
	}

	@Override
	public void viewRemoved(View v) {

	}

	@Override
	public void viewSelected(View v) {
		actionManagerPane.viewSelected(v);
	}

}
