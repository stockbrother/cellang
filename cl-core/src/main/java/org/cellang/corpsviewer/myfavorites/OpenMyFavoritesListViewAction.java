package org.cellang.corpsviewer.myfavorites;

import org.cellang.viewsframework.control.Action;
import org.cellang.viewsframework.ops.OperationContext;

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
