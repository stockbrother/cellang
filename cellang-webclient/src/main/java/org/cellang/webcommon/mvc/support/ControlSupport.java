/**
 * Jun 25, 2012
 */
package org.cellang.webcommon.mvc.support;

import org.cellang.webcore.client.Container;
import org.cellang.webcore.client.lang.Path;
import org.cellang.webcore.client.message.MessageDataWrapper;
import org.cellang.webcore.client.widget.WebWidget;

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
