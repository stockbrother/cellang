package org.cellang.corpsviewer.actions;

import java.util.List;

import org.cellang.viewsframework.HasDelegateUtil;
import org.cellang.viewsframework.PerspectivePanel;
import org.cellang.viewsframework.control.Action;
import org.cellang.viewsframework.control.Refreshable;
import org.cellang.viewsframework.ops.OperationContext;
import org.cellang.viewsframework.view.View;
import org.cellang.viewsframework.view.ViewGroupPanel;

public class RefreshAllViewAction extends Action {
	OperationContext oc;

	public RefreshAllViewAction(OperationContext oc) {
		this.oc = oc;
	}

	@Override
	public String getName() {
		return "Refresh All View";
	}

	@Override
	public void perform() {
		doRefresh(oc);
	}

	public static void doRefresh(OperationContext oc) {
		PerspectivePanel pp = oc.getViewManager();
		List<ViewGroupPanel> vgpL = pp.getViewGroupPanelList();
		for (ViewGroupPanel gp : vgpL) {
			for (View v : gp.getViewList()) {
				Refreshable ref = HasDelegateUtil.getDelegate(v, Refreshable.class, false);
				if (ref != null) {
					ref.refresh();
				}
			}
		}

	}

}
