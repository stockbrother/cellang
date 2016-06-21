/**
 * Jun 25, 2012
 */
package org.cellang.clwt.commons.client;

import org.cellang.clwt.commons.client.frwk.BodyViewI;
import org.cellang.clwt.commons.client.frwk.FrwkViewI;
import org.cellang.clwt.commons.client.mvc.support.AbstractControl;
import org.cellang.clwt.core.client.Container;
import org.cellang.clwt.core.client.lang.Path;
import org.cellang.clwt.core.client.message.MsgWrapper;
import org.cellang.clwt.core.client.widget.WebWidget;

/**
 * @author wuzhen
 * 
 */
public class AbstractCommonsControl extends AbstractControl {

	public AbstractCommonsControl(Container c, String name) {
		super(c, name);
	}

	protected MsgWrapper newRequest(Path path) {
		return new MsgWrapper(path);
	}

	protected void sendMessage(MsgWrapper req) {
		this.getClient(true).getLogicalChannel(true).sendMessage(req);//
	}

	public WebWidget getRootView() {
		return this.getClient(true).getRoot();
	}
	protected FrwkViewI getFrwkView() {
		return this.getRootView().getChild(FrwkViewI.class, true);
	}
	protected BodyViewI getBodyView(){
		return this.getFrwkView().getBodyView();
	}
	
}
