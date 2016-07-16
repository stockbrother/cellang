package org.cellang.console.ops;

import java.util.concurrent.Future;

public abstract class ConsoleOp<T> {

	public Future<T> executeAsync(OperationContext oc) {
		return oc.execute(this);
	}

	public abstract T execute(OperationContext oc);

}
