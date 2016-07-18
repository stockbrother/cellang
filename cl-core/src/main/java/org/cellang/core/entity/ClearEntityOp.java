package org.cellang.core.entity;

import org.cellang.commons.jdbc.DeleteOperation;

public class ClearEntityOp extends EntityOp<Long> {
	EntityConfig ec;

	public ClearEntityOp entityConfig(EntityConfig ec) {
		this.ec = ec;
		return this;
	}

	@Override
	public Long execute(EntitySession es) {

		return es.execute(new DeleteOperation().table(ec.getTableName()));

	}

}
