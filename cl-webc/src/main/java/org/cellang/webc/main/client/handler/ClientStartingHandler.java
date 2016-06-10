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
import org.cellang.clwt.core.client.util.ExceptionUtil;
import org.cellang.webc.main.client.HeaderItems;
import org.cellang.webc.main.client.WebcHandlerSupport;

import com.google.gwt.user.client.Window;

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
		// LOG.debug("handle");//
		// Window.confirm("client starting handler.");//
		FrwkControlI fc = this.getControl(FrwkControlI.class, true);
		// Window.confirm("client starting handler2.");//
		fc.open();

		try {
			fc.openConsoleView(true);//
		} catch (Throwable t) {
			String msg = ExceptionUtil.getStacktraceAsString(t, "\n");
			Window.alert("error to open console view," + msg);
			return;
		}

		// Window.confirm("client starting handler4.");//
		// TODO add handler directory here.
		fc.addHeaderItem(HeaderItems.TOOLS_CONSOLE);
		fc.addHeaderItem(HeaderItems.USER_LOGIN);
		fc.addHeaderItem(HeaderItems.USER_SIGNUP);

	}

}
