/**
 * All right is from Author of the file,to be explained in comming days.
 * May 10, 2013
 */
package org.cellang.commons.session;

import org.cellang.commons.lang.Handler;

/**
 * @author wu
 * 
 */
public interface SessionManager {

	public Session getSession(String id);

	public Session touchSession(String id);

	public Session createSession(long timeout);

	public Session createSession(String id, long timeout);

	public void addCreatedHandler(Handler<Session> hdl);

	public void addTimeoutHandler(Handler<Session> hdl);

	public void addTouchedHandler(Handler<Session> hdl);

}
