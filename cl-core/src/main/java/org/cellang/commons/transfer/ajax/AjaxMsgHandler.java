/**
 * All right is from Author of the file,to be explained in comming days.
 * May 8, 2013
 */
package org.cellang.commons.transfer.ajax;

import org.cellang.commons.session.SessionManager;
import org.cellang.commons.transfer.DefaultCometManager;
import org.cellang.commons.transfer.servlet.AjaxCometServlet;

/**
 * @author wu
 * 
 */
public abstract class AjaxMsgHandler {
	protected SessionManager sessionMap;
	protected DefaultCometManager manager;
	protected boolean sessionRequired;

	public AjaxMsgHandler(boolean sr, SessionManager sessionMap, DefaultCometManager manager) {

		this.sessionMap = sessionMap;
		this.manager = manager;
		this.sessionRequired = sr;
	}

	public void handle(AjaxMsgContext amc) {
		if (this.sessionRequired && amc.arc.as == null) {
			throw new RuntimeException("session required for handler:" + this.getClass()
					+ ",do you provide the session id in http header by key:" + AjaxCometServlet.HK_SESSION_ID);
		}

		this.handlerInternal(amc);

	}

	public abstract void handlerInternal(AjaxMsgContext amc);
}
