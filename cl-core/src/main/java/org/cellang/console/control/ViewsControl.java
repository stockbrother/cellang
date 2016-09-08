package org.cellang.console.control;

import org.cellang.console.control.entity.EntityConfigManager;
import org.cellang.console.view.View;
import org.cellang.console.view.ViewAddListener;
import org.cellang.console.view.PerspectivePanel;
import org.cellang.console.view.ViewSelectionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ViewsControl implements ViewAddListener, ViewSelectionListener {
	private static final Logger LOG = LoggerFactory.getLogger(ViewsControl.class);

	PerspectivePanel views;


	public ViewsControl(EntityConfigManager ecm, PerspectivePanel views) {
		this.views = views;

		this.views.addViewAddListener(this);

	}

	@Override
	public void viewAdded(View v) {
		v.addViewSelectionListener(this);
	}

	@Override
	public void viewSelected(View v) {
		
	}

}
