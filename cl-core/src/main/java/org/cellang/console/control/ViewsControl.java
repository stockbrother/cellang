package org.cellang.console.control;

import org.cellang.console.control.entity.EntityConfigManager;
import org.cellang.console.view.View;
import org.cellang.console.view.ViewsPane;
import org.cellang.console.view.ViewsPane.ViewsListener;
import org.cellang.console.view.helper.HelpersPane;
import org.cellang.console.view.helper.ViewHelperPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ViewsControl implements ViewsListener {
	private static final Logger LOG = LoggerFactory.getLogger(ViewsControl.class);

	ViewsPane views;

	HelpersPane actionManagerPane;

	public ViewsControl(EntityConfigManager ecm, ViewsPane views, HelpersPane actions) {
		this.views = views;
		this.actionManagerPane = actions;
		this.views.addViewsListener(this);

	}

	@Override
	public void viewAdded(View v) {
		return;
	}

	@Override
	public void viewRemoved(View v) {
		//TODO
	}

	@Override
	public void viewSelected(View v) {
		actionManagerPane.viewSelected(v);
	}

}
