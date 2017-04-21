package org.cellang.corpsviewer;

import org.cellang.core.entity.CorpGroupEntity;
import org.cellang.core.entity.EntityOp;
import org.cellang.core.entity.EntitySession;
import org.cellang.viewsframework.PerspectivePanel;
import org.cellang.viewsframework.control.Action;
import org.cellang.viewsframework.ops.OperationContext;

public class DeleteCorpGroupAction extends Action {

	OperationContext oc;

	CorpGroupListView view;

	public DeleteCorpGroupAction(OperationContext oc) {
		this.oc = oc;
	}

	public void setView(CorpGroupListView view) {
		this.view = view;
	}

	@Override
	public String getName() {
		return "Delete Corp Group";
	}

	@Override
	public void perform() {
		if (view == null) {
			return;
		}
		CorpGroupRowData rd = view.getSelectedCorpGroup();
		if (rd == null) {
			return;
		}
		oc.getEntityService().execute(new EntityOp<Long>() {

			@Override
			public Long execute(EntitySession es) {
				return es.delete(CorpGroupEntity.class, rd.corpGroup.getId());
			}
		});
		view.refresh();

	}

}
