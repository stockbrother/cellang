package org.cellang.corpsviewer.myfavorites;

import org.cellang.corpsviewer.QingSuanReportCreater;
import org.cellang.corpsviewer.QingSuanReportView;
import org.cellang.viewsframework.View;
import org.cellang.viewsframework.ops.OperationContext;

public class OpenQinSuanAction extends AbstractFavoriteSelectedCorpAction {

	public OpenQinSuanAction(OperationContext oc) {
		super(oc);
	}

	@Override
	public String getName() {

		return "Qin Suan";

	}

	@Override
	protected void perform(String sid) {
		QingSuanReportCreater c = new QingSuanReportCreater(this.oc, sid, 5);
		c.update(true);
		View v = new QingSuanReportView(oc, 5, sid);
		oc.getViewManager().addView(1, v, true);
	}
}