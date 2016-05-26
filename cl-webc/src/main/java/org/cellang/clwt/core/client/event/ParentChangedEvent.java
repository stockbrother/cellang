/**
 * Jul 18, 2012
 */
package org.cellang.clwt.core.client.event;

import org.cellang.clwt.core.client.lang.WebObject;

/**
 * @author wu
 * 
 */
public class ParentChangedEvent extends Event {

	public static final Type<ParentChangedEvent> TYPE = new Type<ParentChangedEvent>(
			"parent-change");

	public static final String OLD_PARENT = "OLD_PARENT";
	public static final String NEW_PARENT = "NEW_PARENT";

	/** */
	public ParentChangedEvent(WebObject src, WebObject oldP, WebObject newP) {
		super(TYPE, src);
		this.setProperty(OLD_PARENT, oldP);
		this.setProperty(NEW_PARENT, newP);
	}

	public WebObject getOldParent() {
		return (WebObject) this.getProperty(OLD_PARENT);
	}

	public WebObject getNewParent() {
		return (WebObject) this.getProperty(NEW_PARENT);
	}

}
