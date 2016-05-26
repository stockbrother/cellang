/**
 * All right is from Author of the file,to be explained in comming days.
 * May 10, 2013
 */
package org.cellang.core.commons.session;

import org.cellang.core.lang.ServerI;

/**
 * @author wu
 * 
 */
public interface SessionServer extends ServerI{

	public SessionManager createManager(String name);

	public SessionManager getManager(String name);

}
