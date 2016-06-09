package org.cellang.webc.main.client;

import org.cellang.clwt.commons.client.frwk.HeaderItemEvent;
import org.cellang.clwt.commons.client.frwk.impl.FrwkControlImpl;
import org.cellang.clwt.commons.client.frwk.impl.LoginControlImpl;
import org.cellang.clwt.commons.client.mvc.ControlManager;
import org.cellang.clwt.commons.client.mvc.impl.ControlManagerImpl;
import org.cellang.clwt.core.client.ClientObject;
import org.cellang.clwt.core.client.Container;
import org.cellang.clwt.core.client.event.ClientStartedEvent;
import org.cellang.clwt.core.client.event.ClientStartingEvent;
import org.cellang.clwt.core.client.event.EventBus;
import org.cellang.clwt.core.client.event.LogicalChannelBondEvent;
import org.cellang.clwt.core.client.logger.WebLogger;
import org.cellang.clwt.core.client.logger.WebLoggerFactory;
import org.cellang.webc.main.client.event.AutoLoginRequireEvent;
import org.cellang.webc.main.client.handler.ClientStartedHandler;
import org.cellang.webc.main.client.handler.ClientStartingHandler;
import org.cellang.webc.main.client.handler.DispatcherHeaderItemHandler;
import org.cellang.webc.main.client.handler.EndpointBondHandler;
import org.cellang.webc.main.client.handler.action.AutoLoginHandler;

public class CellangClientPluginImpl implements CellangClientPlugin {
	private static final WebLogger LOG = WebLoggerFactory.getLogger(CellangClientPluginImpl.class);

	@Override
	public void active(Container c) {
		LOG.info("active");//
		ClientObject client = c.get(ClientObject.class, true);

		ControlManager manager = new ControlManagerImpl(c);

		manager.parent(client);

		manager.child(new FrwkControlImpl(c, "frwk"));
		manager.child(new LoginControlImpl(c, "login"));

		EventBus eb = client.getEventBus(true);

		eb.addHandler(ClientStartingEvent.TYPE, new ClientStartingHandler(c));// NOTE

		eb.addHandler(ClientStartedEvent.TYPE, new ClientStartedHandler(c));// NOTE

		eb.addHandler(LogicalChannelBondEvent.TYPE, new EndpointBondHandler(c));

		eb.addHandler(AutoLoginRequireEvent.TYPE, new AutoLoginHandler(c));

		eb.addHandler(HeaderItemEvent.TYPE, new DispatcherHeaderItemHandler(c));
		
		LOG.info("end-active");//
		
	}

}
