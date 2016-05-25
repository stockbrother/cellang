/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 6, 2012
 */
package org.cellang.webframework.client.event;

import org.cellang.webframework.client.lang.WebObject;
import org.cellang.webframework.client.logger.WebLogger;

/**
 * @author wu
 * 
 */
public class LogEvent extends Event {

	public static final Event.Type<LogEvent> TYPE = new Event.Type<LogEvent>("log");

	private String message;

	private WebLogger logger;

	/**
	 * @param type
	 */
	public LogEvent(WebLogger logger, String msg, WebObject src) {
		super(TYPE, src);
		this.logger = logger;
		this.message = msg;
	}

	/**
	 * @return the message
	 */
	public String getLogMessage() {
		return message;
	}

}