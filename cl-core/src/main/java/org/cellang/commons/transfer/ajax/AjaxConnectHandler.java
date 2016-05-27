/**
 * All right is from Author of the file,to be explained in comming days.
 * May 8, 2013
 */
package org.cellang.commons.transfer.ajax;

import java.util.UUID;

import org.cellang.commons.session.Session;
import org.cellang.commons.session.SessionManager;
import org.cellang.commons.transfer.DefaultCometManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author wu
 * 
 */
public class AjaxConnectHandler extends AjaxMsgHandler {

	public static final Logger LOG = LoggerFactory.getLogger(AjaxConnectHandler.class);
	/**
	 * @param sessionMap
	 * @param manager
	 */

	public AjaxConnectHandler(SessionManager sessionMap, DefaultCometManager manager) {
		super(false, sessionMap, manager);
	}

	/*
	 * May 8, 2013
	 */
	@Override
	public void handlerInternal(AjaxMsgContext amc) {
		//
		String userAgent = amc.arc.req.getHeader("User-Agent");
		LOG.info("connect:" + "user-agent:" + userAgent);
		
		// do connection
		String sid = UUID.randomUUID().toString();

		AjaxComet as = new AjaxComet(sid);

		Session s = this.sessionMap.createSession(sid, amc.arc.timeoutForSession);//
		s.setProperty(AjaxCometServlet.SK_COMET, as);

		this.manager.onConnect(as);
		// response
		AjaxMsg am2 = new AjaxMsg(AjaxMsg.CONNECT_SUCCESS);
		am2.setProperty(AjaxMsg.PK_CONNECT_SESSION_ID, sid);
		amc.arc.write(am2);

	}
}
