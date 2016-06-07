package org.cellang.webc.main.client;

import org.cellang.clwt.commons.client.frwk.impl.FrwkControlImpl;
import org.cellang.clwt.commons.client.mvc.ControlManager;
import org.cellang.clwt.commons.client.mvc.impl.ControlManagerImpl;
import org.cellang.clwt.core.client.Container;
import org.cellang.clwt.core.client.WebClient;
import org.cellang.clwt.core.client.event.AfterClientStartEvent;
import org.cellang.clwt.core.client.event.EndpointBondEvent;
import org.cellang.clwt.core.client.event.EventBus;
import org.cellang.clwt.core.client.logger.WebLogger;
import org.cellang.clwt.core.client.logger.WebLoggerFactory;
import org.cellang.webc.main.client.handler.ClientStartedHandler;
import org.cellang.webc.main.client.handler.EndpointBondHandler;
import org.cellang.webc.main.client.handler.action.AutoLoginHandler;

public class CellangClientPluginImpl implements CellangClientPlugin {
	private static final WebLogger LOG = WebLoggerFactory.getLogger(CellangClientPluginImpl.class);
	@Override
	public void active(Container c) {
		LOG.info("active");//
		WebClient client = c.get(WebClient.class, true);
		
		ControlManager manager = new ControlManagerImpl(c);
		
		manager.parent(client);
		
		manager.child(new FrwkControlImpl(c, "frwk"));
		
		EventBus eb = client.getEventBus(true);
		
		eb.addHandler(AfterClientStartEvent.TYPE, new ClientStartedHandler(c));//NOTE
		
		eb.addHandler(EndpointBondEvent.TYPE, new EndpointBondHandler(c));
		
		eb.addHandler(AutoLoginRequireEvent.TYPE, new AutoLoginHandler(c));
		LOG.info("end-active");//
	}

}
