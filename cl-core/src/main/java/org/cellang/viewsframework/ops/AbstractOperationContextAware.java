package org.cellang.viewsframework.ops;

public class AbstractOperationContextAware {
	protected OperationContext oc;

	public AbstractOperationContextAware(OperationContext oc) {
		this.oc = oc;
	}
}
