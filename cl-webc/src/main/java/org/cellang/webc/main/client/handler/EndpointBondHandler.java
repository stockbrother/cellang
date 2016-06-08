/**
 *  Jan 31, 2013
 */
package org.cellang.webc.main.client.handler;

import org.cellang.clwt.core.client.Container;
import org.cellang.clwt.core.client.data.ObjectPropertiesData;
import org.cellang.clwt.core.client.event.LogicalChannelBondEvent;
import org.cellang.clwt.core.client.event.Event.EventHandlerI;
import org.cellang.webc.main.client.UiHandlerSupport;
import org.cellang.webc.main.client.event.RegisterUserLoginEvent;
import org.cellang.webc.main.client.event.UserLoginEvent;

/**
 * @author wuzhen
 * 
 */
public class EndpointBondHandler extends UiHandlerSupport implements EventHandlerI<LogicalChannelBondEvent> {

	/**
	 * @param c
	 */
	public EndpointBondHandler(Container c) {
		super(c);
	}

	@Override
	public void handle(LogicalChannelBondEvent e) {
		//
		ObjectPropertiesData ui = e.getChannel().getUserInfo();
		if (ui.getBoolean("isAnonymous", false)) {
			new UserLoginEvent(e.getSource(), ui).dispatch();
		} else {
			new RegisterUserLoginEvent(e.getSource(), ui).dispatch();
		}
	}
}
