/**
 * All right is from Author of the file,to be explained in comming days.
 * May 10, 2013
 */
package org.cellang.commons.session;

/**
 * @author wu
 * 
 */
public class SessionImpl extends SessionSupport {

	/**
	 * @param group
	 */
	public SessionImpl(String id, long timeout) {
		super(id, timeout);
	}

	/*
	 * May 10, 2013
	 */
	@Override
	public void destroy() {
		//

	}

}
