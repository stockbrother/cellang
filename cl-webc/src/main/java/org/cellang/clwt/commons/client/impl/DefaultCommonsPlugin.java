package org.cellang.clwt.commons.client.impl;

import org.cellang.clwt.commons.client.CommonsPlugin;
import org.cellang.clwt.commons.client.mvc.ControlManager;
import org.cellang.clwt.commons.client.mvc.impl.ControlManagerImpl;
import org.cellang.clwt.core.client.Container;
import org.cellang.clwt.core.client.WebClient;

public class DefaultCommonsPlugin implements CommonsPlugin {

	@Override
	public void active(Container c) {
		WebClient client = c.get(WebClient.class, true);
		ControlManager manager = new ControlManagerImpl(c);
		manager.parent(client);
	}

}
