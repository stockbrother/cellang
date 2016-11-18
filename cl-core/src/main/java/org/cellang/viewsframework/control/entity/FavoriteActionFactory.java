package org.cellang.viewsframework.control.entity;

import java.util.ArrayList;
import java.util.List;

import org.cellang.core.entity.EntityConfig;
import org.cellang.core.entity.FavoriteActionEntity;
import org.cellang.viewsframework.ops.OperationContext;
import org.cellang.viewsframework.view.View;

public class FavoriteActionFactory {
	OperationContext oc;

	FavoriteActionFactory(OperationContext oc) {
		this.oc = oc;
	}

	public void execute(FavoriteActionEntity ae) {
		String content = ae.getContent();
		String[] fields = content.split(";");
		String cname = fields[0];
		List<String> extendingPropertyList = new ArrayList<>();
		if (fields.length > 1) {
			String[] cols = fields[1].split(",");
			for (String col : cols) {
				if (col.length() == 0) {
					continue;
				}
				extendingPropertyList.add(col);
			}
		}
		Class cls;
		try {
			cls = Class.forName(cname);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
		EntityConfig ec = this.oc.getEntityConfigFactory().get(cls);
		View view = this.oc.getEntityConfigManager().newEntityListView(ec,extendingPropertyList);
		this.oc.getViewManager().addView(view, true);//
	}
}
