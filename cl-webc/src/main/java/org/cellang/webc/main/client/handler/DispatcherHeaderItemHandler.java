package org.cellang.webc.main.client.handler;

import org.cellang.clwt.commons.client.frwk.HeaderItemEvent;
import org.cellang.clwt.core.client.Container;
import org.cellang.clwt.core.client.event.Event.EventHandlerI;
import org.cellang.clwt.core.client.lang.DispatcherImpl;
import org.cellang.clwt.core.client.lang.Path;
import org.cellang.clwt.core.client.lang.PathBasedDispatcher;
import org.cellang.clwt.core.client.logger.WebLogger;
import org.cellang.clwt.core.client.logger.WebLoggerFactory;
import org.cellang.webc.main.client.HeaderItems;
import org.cellang.webc.main.client.WebcHandlerSupport;

public class DispatcherHeaderItemHandler extends WebcHandlerSupport implements EventHandlerI<HeaderItemEvent> {
	private static final WebLogger LOG = WebLoggerFactory.getLogger(DispatcherHeaderItemHandler.class);

	private PathBasedDispatcher dispatcher;

	public DispatcherHeaderItemHandler(Container c) {
		super(c);
		this.dispatcher = new DispatcherImpl("header-item-handler");
		this.dispatcher.addHandler(HeaderItems.USER_LOGIN, new UserLoginHeaderItemHandler(c));
	}

	@Override
	public void handle(HeaderItemEvent t) {
		Path p = t.getHeaderItemPath();// not event path!

		LOG.debug("header itme event, herder item path:" + p);

		this.dispatcher.dispatch(p, t);//
	}

}
