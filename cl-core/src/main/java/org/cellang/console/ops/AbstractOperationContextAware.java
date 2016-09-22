package org.cellang.console.ops;

public class AbstractOperationContextAware {
	protected OperationContext oc;

	public AbstractOperationContextAware(OperationContext oc) {
		this.oc = oc;
	}
}
