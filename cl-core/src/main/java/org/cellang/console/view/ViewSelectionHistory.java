package org.cellang.console.view;

import java.util.ArrayList;
import java.util.List;

import org.cellang.console.control.EventListener;
import org.cellang.console.ops.AbstractOperationContextAware;
import org.cellang.console.ops.OperationContext;

public class ViewSelectionHistory extends AbstractOperationContextAware implements EventListener {

	List<View> viewList = new ArrayList<>();

	public ViewSelectionHistory(OperationContext oc) {
		super(oc);
		oc.getEventBus().addEventListener(ViewGroupPanel.ViewSelectionEvent.class, this);
	}

	@Override
	public void onEvent(Object evt) {
		if (evt instanceof ViewGroupPanel.ViewSelectionEvent) {

			ViewGroupPanel.ViewSelectionEvent e = (ViewGroupPanel.ViewSelectionEvent) evt;
			View v = e.getView();
			viewList.remove(v);
			viewList.add(0, v);
		} else if (evt instanceof ViewGroupPanel.ViewRemoveEvent) {

			ViewGroupPanel.ViewRemoveEvent e = (ViewGroupPanel.ViewRemoveEvent) evt;
			View v = e.getView();
			viewList.remove(v);
		}
	}

}
