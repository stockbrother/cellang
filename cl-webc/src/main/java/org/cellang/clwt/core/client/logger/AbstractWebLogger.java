/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 8, 2012
 */
package org.cellang.clwt.core.client.logger;

import java.util.HashMap;
import java.util.Map;

import org.cellang.clwt.core.client.logger.WebLoggerFactory.Configuration;

/**
 * @author wu
 * 
 */
public abstract class AbstractWebLogger implements WebLogger {

	protected String name;

	protected static Map<Integer, String> levelName = new HashMap<Integer, String>();

	static {
		levelName.put(WebLogger.LEVEL_DEBUG, "DEBUG");
		levelName.put(WebLogger.LEVEL_INFO, "INFO");
		levelName.put(WebLogger.LEVEL_WARN, "ERROR");
		levelName.put(WebLogger.LEVEL_ERROR, "ERROR");

	}

	public AbstractWebLogger(String name2) {
		this.name = name2;
	}
	
	@Override
	public boolean isTraceEnabled() {
		return this.isLevelEnabled(LEVEL_TRACE);
	}	

	@Override
	public void trace(Object msg) {
		log(WebLogger.LEVEL_TRACE, msg);
	}
	@Override
	public void debug(Object msg) {

		log(WebLogger.LEVEL_DEBUG, msg);

	}

	/*
	 * Nov 8, 2012
	 */
	@Override
	public String getName() {
		//
		return name;
	}

	/*
	 * Nov 8, 2012
	 */
	@Override
	public void info(Object msg) {
		log(WebLogger.LEVEL_INFO, msg);
	}

	public boolean isLevelEnabled(int level) {

		return this.getLevel() >= level;
	}

	public int getLevel() {
		Configuration cfg = WebLoggerFactory.getConfiguration4Logger(this.name);
		return cfg.getLoggerLevel();
	}

	public void log(int level, Object msg) {
		log(level, msg, null);
	}

	public void log(int level, Object msg, Throwable t) {
		if (!this.isLevelEnabled(level)) {
			return;// ignore

		}
		this.doLog(level, msg, t);
	}

	protected abstract void doLog(int level, Object msg, Throwable t);

	@Override
	public void error(Object msg) {
		this.error(msg, null);
	}

	@Override
	public void error(Object msg, Throwable t) {
		log(WebLogger.LEVEL_ERROR, msg, t);
	}

	@Override
	public boolean isDebugEnabled() {
		//
		return this.isLevelEnabled(LEVEL_DEBUG);

	}
}
