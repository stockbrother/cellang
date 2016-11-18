package org.cellang.viewsframework.ops;

import java.util.concurrent.ExecutionException;

public class ResetOp extends ConsoleOp<Void> {

	@Override
	public Void execute(OperationContext oc) {

		try {
			oc.execute(new ClearOp()).get();
			oc.execute(new LoadOp("corps")).get();
			oc.execute(new WashOp("163")).get();
			oc.execute(new LoadOp("163pp")).get();

		} catch (InterruptedException | ExecutionException e) {
			throw new RuntimeException(e);
		}
		return null;
	}

}
