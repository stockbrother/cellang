package org.cellang.webc.main.client;

import org.cellang.clwt.commons.client.frwk.FrwkControlI;
import org.cellang.clwt.commons.client.frwk.impl.FrwkControlImpl;
import org.cellang.clwt.commons.client.mvc.ActionEvent;
import org.cellang.clwt.commons.client.mvc.ControlManager;
import org.cellang.clwt.commons.client.mvc.impl.ControlManagerImpl;
import org.cellang.clwt.core.client.ClientObject;
import org.cellang.clwt.core.client.Container;
import org.cellang.clwt.core.client.event.ClientStartedEvent;
import org.cellang.clwt.core.client.event.ClientStartingEvent;
import org.cellang.clwt.core.client.event.EventBus;
import org.cellang.clwt.core.client.lang.InstanceOf;
import org.cellang.clwt.core.client.lang.InstanceOf.CheckerSupport;
import org.cellang.clwt.core.client.logger.WebLogger;
import org.cellang.clwt.core.client.logger.WebLoggerFactory;
import org.cellang.webc.main.client.event.AutoLoginRequireEvent;
import org.cellang.webc.main.client.handler.ClientStartedHandler;
import org.cellang.webc.main.client.handler.ClientStartingHandler;
import org.cellang.webc.main.client.handler.action.AutoLoginHandler;
import org.cellang.webc.main.client.handler.action.DispatcherActionHandler;
import org.cellang.webc.main.client.widget.LoginViewI;

public class CellangClientPluginImpl implements CellangClientPlugin {
	private static final WebLogger LOG = WebLoggerFactory.getLogger(CellangClientPluginImpl.class);

	@Override
	public void active(Container c) {
		LOG.info("active");//

		this.activeInstanceOf(c);

		ClientObject client = c.getClient(true);

		ControlManager manager = new ControlManagerImpl(c);
		client.setProperty(ControlManager.class.getName(), manager);//

		manager.addControl(FrwkControlI.class, new FrwkControlImpl(c, "frwk"));
		manager.addControl(LoginControlI.class, new LoginControlImpl(c, "login"));
		manager.addControl(MainControlI.class, new MainControl(c, "main"));

		EventBus eb = client.getEventBus(true);

		eb.addHandler(ClientStartingEvent.TYPE, new ClientStartingHandler(c));// NOTE

		eb.addHandler(ClientStartedEvent.TYPE, new ClientStartedHandler(c));// NOTE

		eb.addHandler(AutoLoginRequireEvent.TYPE, new AutoLoginHandler(c));

		// action
		eb.addHandler(ActionEvent.TYPE, new DispatcherActionHandler(c));

		LOG.info("end-active");//

	}

	private void activeInstanceOf(Container c) {

		InstanceOf.addChecker(new CheckerSupport(LoginControlI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof LoginControlI;
			}
		});

		InstanceOf.addChecker(new CheckerSupport(LoginViewI.class) {

			@Override
			public boolean isInstance(Object o) {

				return o instanceof LoginViewI;

			}

		});
		InstanceOf.addChecker(new InstanceOf.CheckerSupport(MainControlI.class) {

			@Override
			public boolean isInstance(Object o) {
				// TODO Auto-generated method stub
				return o instanceof MainControlI;
			}
		});
	}

}
