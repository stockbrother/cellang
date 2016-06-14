package org.cellang.core.server;

import org.cellang.elastictable.TableService;

public abstract class AbstracHandler implements MessageHandler {

	protected TableService tableService;

	public AbstracHandler(TableService ts) {
		this.tableService = ts;
	}
}
