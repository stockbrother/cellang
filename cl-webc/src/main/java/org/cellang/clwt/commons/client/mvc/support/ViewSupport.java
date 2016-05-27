/**
 * Jun 25, 2012
 */
package org.cellang.clwt.commons.client.mvc.support;



import org.cellang.clwt.commons.client.mvc.ActionEvent;
import org.cellang.clwt.commons.client.mvc.Control;
import org.cellang.clwt.commons.client.mvc.ControlManager;
import org.cellang.clwt.commons.client.mvc.ViewI;
import org.cellang.clwt.commons.client.mvc.widget.LayoutSupport;
import org.cellang.clwt.core.client.Container;
import org.cellang.clwt.core.client.WebException;
import org.cellang.clwt.core.client.data.ErrorInfosData;
import org.cellang.clwt.core.client.data.MessageData;
import org.cellang.clwt.core.client.lang.Path;
import org.cellang.clwt.core.client.message.MessageDataWrapper;
import org.cellang.clwt.core.client.transfer.TransferPoint;

import com.google.gwt.user.client.Element;

/**
 * @author wuzhen
 * 
 */
public class ViewSupport extends LayoutSupport implements ViewI {

	public ViewSupport(Container c, Element ele) {
		this(c, null, ele);
	}

	public ViewSupport(Container c, String name, Element ele) {
		super(c, name, ele);
	}

	@Override
	public void clickAction(Path a) {
		throw new WebException("TODO");
	}

	/**
	 * @param name
	 */
	protected void dispatchActionEvent(Path name) {
		ActionEvent ae = this.newActionEvent(name);
		this.beforeActionEvent(ae);
		ae.dispatch();
	}

	//
	protected ActionEvent newActionEvent(Path aname) {
		ActionEvent rt = new ActionEvent(this, (aname));

		return rt;
	}

	protected void beforeActionEvent(ActionEvent ae) {

	}

	protected TransferPoint getEndpoint() {
		return this.getClient(true).getEndpoint(true);
	}

	protected void sendMessage(MessageDataWrapper req) {
		this.getEndpoint().sendMessage(req);
	}

	protected void sendMessage(MessageData req) {
		this.getEndpoint().sendMessage(req);
	}

	protected <T extends Control> T getControl(Class<T> cls, boolean force) {
		return this.getControlManager().getControl(cls, force);
	}

	protected ControlManager getControlManager() {

		return this.getClient(true).getChild(ControlManager.class, true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fs.uicommons.api.gwt.client.mvc.ViewI#addErrorInfo(com.fs.uicore.
	 * api.gwt.client.data.ErrorInfosData)
	 */
	@Override
	public void addErrorInfo(ErrorInfosData eis) {

	}

	@Override
	public void clearErrorInfo() {

	}
}
