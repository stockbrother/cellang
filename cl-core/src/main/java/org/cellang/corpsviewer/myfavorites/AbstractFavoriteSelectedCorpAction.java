package org.cellang.corpsviewer.myfavorites;

import org.cellang.viewsframework.control.Action;
import org.cellang.viewsframework.ops.OperationContext;

public abstract class AbstractFavoriteSelectedCorpAction extends Action {
	OperationContext oc;
	MyFavoritesCorpListView corpListView;

	public AbstractFavoriteSelectedCorpAction(OperationContext oc) {
		this.oc = oc;
	}

	public void setCorpListView(MyFavoritesCorpListView v) {
		this.corpListView = v;
	}

	@Override
	public void perform() {
		String sid = this.corpListView.getSelectedCorpId();
		if (sid == null) {
			return;
		}
		this.perform(sid);
	}

	protected abstract void perform(String selectedCorpId);

}
