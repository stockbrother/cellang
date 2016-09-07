package org.cellang.console.control;

import org.cellang.console.control.entity.EntityConfigManager;
import org.cellang.console.view.View;
import org.cellang.console.view.ViewGroupsPanel;
import org.cellang.console.view.ViewSelectionListener;
import org.cellang.console.view.ViewAddListener;
import org.cellang.console.view.helper.HelpersPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ViewsControl implements ViewAddListener, ViewSelectionListener {
	private static final Logger LOG = LoggerFactory.getLogger(ViewsControl.class);

	ViewGroupsPanel views;

	HelpersPane helpers;

	public ViewsControl(EntityConfigManager ecm, ViewGroupsPanel views, HelpersPane actions) {
		this.views = views;
		this.helpers = actions;
		this.views.addViewAddListener(this);

	}

	@Override
	public void viewAdded(View v) {
		v.addViewSelectionListener(this);
	}

	@Override
	public void viewSelected(View v) {
		helpers.viewSelected(v);
	}

}
