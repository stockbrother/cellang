package org.cellang.console.model;

import java.util.List;

import org.cellang.console.ext.ExtendingPropertyDefine;
import org.cellang.core.entity.EntityConfig;
import org.cellang.core.entity.EntityObject;
import org.cellang.core.entity.EntityOp;
import org.cellang.core.entity.EntitySession;
import org.cellang.core.entity.EntitySessionFactory;
import org.cellang.core.entity.ExtendingPropertyEntity;

public class ExtendingPropertyUpdater {

	ExtendingPropertyDefine define;
	EntitySessionFactory esf;

	public ExtendingPropertyUpdater(ExtendingPropertyDefine pd, EntitySessionFactory esf) {
		this.define = pd;
		this.esf = esf;
	}

	public void execute() {

		this.esf.execute(new EntityOp<Void>() {

			@Override
			public Void execute(EntitySession es) {
				doExecute(es);
				return null;
			}

		});
	}

	private void doExecute(EntitySession es) {
		this.define.install(this.esf);
		EntityConfig ec = esf.getEntityConfigFactory().getEntityConfig(this.define.getEntityClass());
		String type = ec.getEntityClass().getName();
		String key = define.getKey();
		long deleted = es.delete(ExtendingPropertyEntity.class, new String[] { "entityType", "key" },
				new Object[] { ec.getEntityClass().getName(), define.getKey() });

		List<? extends EntityObject> l = es.query(ec.getEntityClass()).execute(es);
		for (EntityObject eo : l) {
			String eid = eo.getId();
			Object obj = define.getValue(eo);
			ExtendingPropertyEntity ep = new ExtendingPropertyEntity();
			ep.setId(type + "[" + eid + "]." + key);
			ep.setEntityId(eo.getId());
			ep.setEntityType(type);
			ep.setKey(key);

			ep.setValue(toDouble(obj));
			es.save(ep);

		}

	}

	private Double toDouble(Object obj) {
		if (obj == null) {
			return null;
		}
		if (obj instanceof Number) {
			return ((Number) obj).doubleValue();
		}
		throw new RuntimeException("cannot cast to double from:" + obj);
	}
}
