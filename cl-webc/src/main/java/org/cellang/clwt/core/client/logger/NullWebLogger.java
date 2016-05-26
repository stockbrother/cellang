/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 11, 2012
 */
package org.cellang.clwt.core.client.logger;

/**
 * @author wu
 * 
 */
public class NullWebLogger extends AbstractWebLogger {

	/**
	 * @param level
	 * @param cls
	 */
	public NullWebLogger(String name) {
		super(name);
	}

	@Override
	public boolean isLevelEnabled(int level) {
		return false;
	}

	@Override
	public void log(int level, Object msg) {
		// do nothing.
	}

	/*
	 * Nov 11, 2012
	 */
	@Override
	protected void doLog(int level, Object msg, Throwable t) {
		//

	}

}
