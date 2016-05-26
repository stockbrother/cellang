/**
 * Jun 25, 2012
 */
package org.cellang.clwt.commons.client.mvc.support;

import org.cellang.clwt.core.client.Container;
import org.cellang.clwt.core.client.lang.Path;
import org.cellang.clwt.core.client.message.MessageDataWrapper;
import org.cellang.clwt.core.client.widget.WebWidget;

/**
 * @author wuzhen
 * 
 */
public class ControlSupport extends AbstractControl {

	public ControlSupport(Container c, String name) {
		super(c, name);
	}

	protected MessageDataWrapper newRequest(Path path) {
		return new MessageDataWrapper(path);
	}

	protected void sendMessage(MessageDataWrapper req) {
		this.getClient(true).getEndpoint(true).sendMessage(req);//
	}

	public WebWidget getRootView() {
		return this.getClient(true).getRoot();
	}
	
}
