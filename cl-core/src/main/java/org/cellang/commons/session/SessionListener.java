/**
 * All right is from Author of the file,to be explained in comming days.
 * May 10, 2013
 */
package org.cellang.commons.session;

/**
 * @author wu
 * 
 */
public interface SessionListener {

	public void onCreated(Session session);

	public void onTouched(Session session);

	public void onTimeout(Session session);

}
