package org.cellang.console.menubar;

import org.cellang.console.control.Action;
import org.cellang.console.corpgrouping.MyFavoritesCorpListView;
import org.cellang.console.ops.OperationContext;

public class OpenMyFavoritesListViewAction extends Action {
	OperationContext oc;

	public OpenMyFavoritesListViewAction(OperationContext oc) {
		this.oc = oc;
	}

	@Override
	public String getName() {
		return "MyFavorites";
	}

	@Override
	public void perform() {
		MyFavoritesCorpListView view = new MyFavoritesCorpListView(oc);
		oc.getViewManager().addView(view, true);		
	}

}
