package org.cellang.corpsviewer;

import org.cellang.core.entity.CorpInfoEntity;
import org.cellang.core.entity.GroupCorpEntity;
import org.cellang.corpsviewer.actions.RefreshAllViewAction;
import org.cellang.corpsviewer.myfavorites.MyFavoritesCorpListView;
import org.cellang.viewsframework.control.Action;
import org.cellang.viewsframework.ops.OperationContext;

public class AddToMyFavoritesAction extends Action {
	OperationContext oc;

	CorpListView listView;

	public AddToMyFavoritesAction(OperationContext oc) {
		this.oc = oc;
	}

	public void setListView(CorpListView listView) {
		this.listView = listView;
	}

	@Override
	public String getName() {
		return "Add to My Favorites";
	}

	@Override
	public void perform() {
		CorpInfoEntity e = listView.getSelected();
		if (e == null) {
			return;
		}
		GroupCorpEntity gc = new GroupCorpEntity();

		gc.setId(MyFavoritesCorpListView.GROUP_ID + "." + e.getId());
		gc.setCorpId(e.getId());
		gc.setGroupId(MyFavoritesCorpListView.GROUP_ID);

		this.oc.getEntityService().save(gc);

		RefreshAllViewAction.doRefresh(oc);
	}

}
