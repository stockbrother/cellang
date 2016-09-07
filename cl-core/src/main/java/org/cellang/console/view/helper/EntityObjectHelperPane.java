package org.cellang.console.view.helper;

import java.util.HashMap;
import java.util.Map;

import org.cellang.console.control.HasActions;
import org.cellang.console.control.entity.EntityConfigControl;
import org.cellang.console.control.entity.EntityConfigManager;
import org.cellang.core.entity.EntityObject;

public class EntityObjectHelperPane extends HelperPane<EntityObject> {
	EntityConfigManager ecm;

	public EntityObjectHelperPane() {
		super("EntityObjectHelper");
	}

	/**
	 * TODO use a container for global reference transfer.
	 * 
	 * @param ecm
	 */
	public void setEntityConfigManager(EntityConfigManager ecm) {
		this.ecm = ecm;
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

}
