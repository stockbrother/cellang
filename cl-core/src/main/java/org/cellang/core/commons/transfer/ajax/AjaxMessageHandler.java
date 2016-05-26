/**
 * All right is from Author of the file,to be explained in comming days.
 * May 8, 2013
 */
package org.cellang.core.commons.transfer.ajax;

import org.cellang.core.commons.session.SessionManager;
import org.cellang.core.commons.transfer.DefaultCometManager;

/**
 * @author wu
 * 
 */
public class AjaxMessageHandler extends AjaxMsgHandler {

	/**
	 * @param sessionMap
	 * @param manager
	 */

	public AjaxMessageHandler(SessionManager sessionMap, DefaultCometManager manager) {
		super(true, sessionMap, manager);
	}

	/*
	 * May 8, 2013
	 */
	@Override
	public void handlerInternal(AjaxMsgContext amc) {
		String msg = (String) amc.am.getProperty(AjaxMsg.PK_TEXTMESSAGE, true);
		this.manager.onMessage(amc.arc.as, msg);

	}
}
