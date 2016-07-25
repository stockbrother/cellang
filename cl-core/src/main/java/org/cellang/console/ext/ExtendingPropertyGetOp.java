package org.cellang.console.ext;

import java.util.List;

import org.cellang.core.entity.EntityOp;
import org.cellang.core.entity.EntitySession;
import org.cellang.core.entity.ExtendingPropertyEntity;

public class ExtendingPropertyGetOp extends EntityOp<ExtendingPropertyEntity> {

	Class entityType;
	String entityId;
	String key;

	public ExtendingPropertyGetOp set(Class entityType, String entityId, String key) {
		this.entityId = entityId;
		this.entityType = entityType;
		this.key = key;
		return this;
	}

	@Override
	public ExtendingPropertyEntity execute(EntitySession es) {
		List<ExtendingPropertyEntity> l = es
				.query(ExtendingPropertyEntity.class, new String[] { "entityType", "entityId", "key" },
						new Object[] { this.entityType.getName(), this.entityId, this.key })
				.execute(es);
		if (l.size() == 0) {
			return null;
		} else if (l.size() == 1) {
			return l.get(0);
		} else {
			throw new RuntimeException("bug");
		}
	}

}
