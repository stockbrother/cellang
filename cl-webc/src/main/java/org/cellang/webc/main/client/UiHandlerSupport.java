/**
 * All right is from Author of the file,to be explained in comming days.
 * Jan 14, 2013
 */
package org.cellang.webc.main.client;

import org.cellang.clwt.commons.client.mvc.Control;
import org.cellang.clwt.commons.client.mvc.ControlManager;
import org.cellang.clwt.core.client.Container;
import org.cellang.clwt.core.client.WebClient;
import org.cellang.clwt.core.client.lang.Path;
import org.cellang.clwt.core.client.message.MsgWrapper;
import org.cellang.clwt.core.client.transfer.TransferPoint;
import org.cellang.clwt.core.client.widget.WebWidget;

/**
 * @author wu
 * 
 */
public class UiHandlerSupport {

	protected Container container;

	public UiHandlerSupport(Container c) {
		this.container = c;
	}

	protected TransferPoint getEndpoint() {
		return this.getClient(true).getEndpoint(true);
	}

	protected MsgWrapper newRequest(Path path) {
		return new MsgWrapper(path);
	}

	protected void sendMessage(MsgWrapper req) {
		this.getClient(true).getEndpoint(true).sendMessage(req);//
	}

	protected ControlManager getControlManager() {
		return this.getClient(true).getChild(ControlManager.class, true);
	}

	protected WebClient getClient(boolean force) {
		return this.container.get(WebClient.class, force);
	}

	protected WebWidget getRootView() {
		return this.getClient(true).getRoot();
	}

	protected <T extends Control> T getControl(Class<T> cls, boolean force) {
		return this.getControlManager().getControl(cls, force);
	}
}
