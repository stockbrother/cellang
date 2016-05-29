/**
 * Jun 30, 2012
 */
package org.cellang.clwt.commons.client.widget;

import org.cellang.clwt.core.client.event.Event;
import org.cellang.clwt.core.client.lang.WebObject;

/**
 * @author wu
 * 
 */
public class SelectEvent extends Event {

	public static Type<SelectEvent> TYPE = new Type<SelectEvent>("select");

	private boolean selected;

	/** */
	public SelectEvent(WebObject source, boolean sel) {
		super(TYPE, source);
		this.selected = sel;
	}

	public boolean isSelected() {
		return selected;
	}
}
