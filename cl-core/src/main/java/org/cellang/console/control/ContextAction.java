package org.cellang.console.control;

public abstract class ContextAction extends Action{

	protected Object contextObject;
	public void setContextObject(Object context) {
		this.contextObject = context;
	}

}
