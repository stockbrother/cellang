package org.cellang.console.control;

import org.cellang.commons.util.UUIDUtil;

public abstract class Action {
	protected String id = UUIDUtil.randomStringUUID();

	public String getId() {
		return this.id;
	}

	public abstract String getName();

	public abstract void perform();
}
