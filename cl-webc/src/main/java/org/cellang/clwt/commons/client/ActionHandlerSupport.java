/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 11, 2012
 */
package org.cellang.clwt.commons.client;

import org.cellang.clwt.commons.client.mvc.ActionEvent;
import org.cellang.clwt.commons.client.mvc.ViewI;
import org.cellang.clwt.core.client.Container;
import org.cellang.clwt.core.client.event.Event.EventHandlerI;

/**
 * @author wu
 * 
 */
public abstract class ActionHandlerSupport extends UiHandlerSupport implements EventHandlerI<ActionEvent> {

	public ActionHandlerSupport(Container c) {
		super(c);
	}

	protected <T extends ViewI> T findView(Class<T> vcls, boolean force) {
		return this.getClient(true).getRoot().find(vcls, force);
	}
}
