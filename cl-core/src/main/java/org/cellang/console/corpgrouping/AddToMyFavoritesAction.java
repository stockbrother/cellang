package org.cellang.console.corpgrouping;

import org.cellang.console.control.Action;
import org.cellang.console.menubar.RefreshAllViewAction;
import org.cellang.console.ops.OperationContext;
import org.cellang.core.entity.CorpInfoEntity;
import org.cellang.core.entity.GroupCorpEntity;

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
