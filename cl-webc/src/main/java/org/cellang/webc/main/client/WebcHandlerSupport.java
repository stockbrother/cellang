/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 14, 2013
 */
package org.cellang.webc.main.client;

import org.cellang.clwt.commons.client.mvc.Control;
import org.cellang.clwt.commons.client.mvc.ControlManager;
import org.cellang.clwt.core.client.Container;
import org.cellang.clwt.core.client.ClientObject;
import org.cellang.clwt.core.client.lang.Path;
import org.cellang.clwt.core.client.message.MsgWrapper;
import org.cellang.clwt.core.client.transfer.LogicalChannel;
import org.cellang.clwt.core.client.widget.WebWidget;

/**
 * @author wu <br>
 *         TODO rename to a more concrete name.
 */
public class WebcHandlerSupport {

	protected Container container;

	public WebcHandlerSupport(Container c) {
		this.container = c;
	}

	protected LogicalChannel getEndpoint() {
		return this.getClient(true).getLogicalChannel(true);
	}

	protected MsgWrapper newRequest(Path path) {
		return new MsgWrapper(path);
	}

	protected void sendMessage(MsgWrapper req) {
		this.getClient(true).getLogicalChannel(true).sendMessage(req);//
	}

	protected ControlManager getControlManager() {
		return this.getClient(true).getChild(ControlManager.class, true);
	}

	protected ClientObject getClient(boolean force) {
		return this.container.get(ClientObject.class, force);
	}

	protected WebWidget getRootView() {
		return this.getClient(true).getRoot();
	}

	protected <T extends Control> T getControl(Class<T> cls, boolean force) {
		return this.getControlManager().getControl(cls, force);
	}
}
