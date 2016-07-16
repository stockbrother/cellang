package org.cellang.console.ops;

import org.cellang.core.entity.EntityOp;
import org.cellang.core.entity.EntitySession;

public class ClearOp extends ConsoleOp<Void> {

	@Override
	public Void execute(OperationContext oc) {
		oc.getViewManager().clear();
		EntityOp<Void> op = new EntityOp<Void>() {

			@Override
			public Void execute(EntitySession es) {
				es.clear();
				return null;
			}
		};
		oc.getEntityService().execute(op);
		return null;
	}

}
