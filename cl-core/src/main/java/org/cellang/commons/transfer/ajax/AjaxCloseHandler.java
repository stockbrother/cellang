/**
 * All right is from Author of the file,to be explained in comming days.
 * May 8, 2013
 */
package org.cellang.commons.transfer.ajax;

import org.cellang.commons.session.SessionManager;
import org.cellang.commons.transfer.DefaultCometManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author wu
 * 
 */
public class AjaxCloseHandler extends AjaxMsgHandler {
	public static final Logger LOG = LoggerFactory.getLogger(AjaxCloseHandler.class);
	/**
	 * @param sessionMap
	 * @param manager
	 */

	public AjaxCloseHandler(SessionManager sessionMap, DefaultCometManager manager) {
		super(true, sessionMap, manager);
	}

	/*
	 * May 8, 2013
	 */
	@Override
	public void handlerInternal(AjaxMsgContext amc) {
		LOG.info("close:");
		amc.arc.writeCloseSuccess();
		if (amc.arc.as == null) {
			// ignore the session already closed.
			return;
		}
		//TODO remove the session from manager.
		//this.sessionMap.
		// fetch?
		// this.fetchMessage(amc);
		this.manager.onClose(amc.arc.as, 0, "");

	}
}
