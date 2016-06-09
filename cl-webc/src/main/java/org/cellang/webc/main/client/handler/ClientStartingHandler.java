/**
 *  Jan 31, 2013
 */
package org.cellang.webc.main.client.handler;

import org.cellang.clwt.commons.client.frwk.FrwkControlI;
import org.cellang.clwt.core.client.Container;
import org.cellang.clwt.core.client.event.ClientStartingEvent;
import org.cellang.clwt.core.client.event.Event.EventHandlerI;
import org.cellang.clwt.core.client.logger.WebLogger;
import org.cellang.clwt.core.client.logger.WebLoggerFactory;
import org.cellang.webc.main.client.HeaderItems;
import org.cellang.webc.main.client.WebcHandlerSupport;

/**
 * @author wuzhen <br>
 *         The first event after client started is called.
 */
public class ClientStartingHandler extends WebcHandlerSupport implements EventHandlerI<ClientStartingEvent> {
	private static final WebLogger LOG = WebLoggerFactory.getLogger(ClientStartingHandler.class);

	/**
	 * @param c
	 */
	public ClientStartingHandler(Container c) {
		super(c);
	}

	@Override
	public void handle(ClientStartingEvent e) {
		// start frwk view.
		FrwkControlI fc = this.getControl(FrwkControlI.class, true);
		fc.open();
		fc.openConsoleView(true);//		
		fc.addHeaderItem(HeaderItems.USER_LOGIN);
	}

}
