/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 11, 2012
 */
package org.cellang.clwt.commons.client.mvc;

import org.cellang.clwt.core.client.event.Event;
import org.cellang.clwt.core.client.lang.Path;
import org.cellang.clwt.core.client.lang.WebObject;

/**
 * @author wu
 * 
 * 
 */
public class ActionEvent extends Event {

	public static final Type<ActionEvent> TYPE = new Type<ActionEvent>("action");

	private Path actionPath;
	
	/**
	 * @param src
	 * @param code
	 */
	public ActionEvent(WebObject src, Path p) {
		super(TYPE, src);
		this.actionPath = p;
	}

	/**
	 * @return the action
	 */
	public Path getActionPath() {
		return this.actionPath;
	}

}
