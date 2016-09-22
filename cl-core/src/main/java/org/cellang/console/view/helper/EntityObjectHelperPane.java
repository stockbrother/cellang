package org.cellang.console.view.helper;

import java.util.HashMap;
import java.util.Map;

import org.cellang.console.control.EventListener;
import org.cellang.console.control.HasActions;
import org.cellang.console.control.entity.EntityConfigControl;
import org.cellang.console.control.entity.EntityConfigManager;
import org.cellang.console.ops.OperationContext;
import org.cellang.console.view.table.EntityObjectTableView;
import org.cellang.core.entity.EntityObject;

public class EntityObjectHelperPane extends HelperPane<EntityObject> implements EventListener {
	EntityConfigManager ecm;
	OperationContext oc;

	public EntityObjectHelperPane(OperationContext oc) {
		super("EntityObjectHelper", oc);
		this.oc = oc;
		this.oc.getEventBus().addEventListener(EntityObjectTableView.EntityObjectSelectionEvent.class, this);
		this.ecm = oc.getEntityConfigManager();
	}

	@Override
	public void setContextObject(EntityObject co) {
		super.setContextObject(co);
		if (co == null) {
			return;
		}
		Class<?> cls = co.getClass();
		EntityConfigControl<?> ecc = this.ecm.getEntityConfigControl(cls);
		if (ecc != null) {
			HasActions has = ecc.getDelegate(HasActions.class);
			if (has != null) {
				super.addActions(co, has);
			}
		}
		Map<String, Object> des = new HashMap<String, Object>();
		des.put("class", co.getClass().getName());
		des.put("id", co.getId());//

		this.addText(des);
	}

	@Override
	public void onEvent(Object evt) {
		EntityObjectTableView.EntityObjectSelectionEvent eose = (EntityObjectTableView.EntityObjectSelectionEvent) evt;
		this.setContextObject(eose.entityObject);
	}

}
