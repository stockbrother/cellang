package org.cellang.core.server;

import org.cellang.commons.lang.Handler;
import org.cellang.elastictable.TableService;

public abstract class AbstracHandler implements Handler<MessageContext> {

	protected TableService tableService;

	public AbstracHandler(TableService ts) {
		this.tableService = ts;
	}
}
