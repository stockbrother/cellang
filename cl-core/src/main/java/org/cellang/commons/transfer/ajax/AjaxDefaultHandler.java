/**
 * All right is from Author of the file,to be explained in comming days.
 * May 8, 2013
 */
package org.cellang.commons.transfer.ajax;

import org.cellang.commons.session.SessionManager;
import org.cellang.commons.transfer.DefaultCometManager;

/**
 * @author wu
 * 
 */
public class AjaxDefaultHandler extends AjaxMsgHandler {

	/**
	 * @param sessionMap
	 * @param manager
	 */

	public AjaxDefaultHandler(SessionManager sm, DefaultCometManager manager) {
		super(true, sm, manager);
	}

	/*
	 * May 8, 2013
	 */
	@Override
	public void handlerInternal(AjaxMsgContext amc) {

		amc.arc.writeError("no-handler", "for path:" + amc.am.getPath().toString());

	}
}
