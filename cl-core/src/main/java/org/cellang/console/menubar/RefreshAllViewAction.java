package org.cellang.console.menubar;

import java.util.List;

import org.cellang.console.HasDelegateUtil;
import org.cellang.console.control.Action;
import org.cellang.console.control.Refreshable;
import org.cellang.console.ops.OperationContext;
import org.cellang.console.view.PerspectivePanel;
import org.cellang.console.view.View;
import org.cellang.console.view.ViewGroupPanel;

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
		PerspectivePanel pp = oc.getViewManager();
		List<ViewGroupPanel> vgpL = pp.getViewGroupPanelList();
		for (ViewGroupPanel gp : vgpL) {
			for (View v : gp.getViewList()) {
				Refreshable ref = HasDelegateUtil.getDelegate(gp, Refreshable.class, false);
				if (ref != null) {
					ref.refresh();
				}
			}
		}

	}

}
