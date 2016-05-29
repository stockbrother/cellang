/**
 * Jun 30, 2012
 */
package org.cellang.clwt.commons.client.widget;

import org.cellang.clwt.core.client.event.Event;

/**
 * @author wu
 * 
 */
public class ClosingEvent extends Event {

	public static Type<ClosingEvent> TYPE = new Type<ClosingEvent>("closing");

	/** */
	public ClosingEvent(PanelWI source) {
		super(TYPE, source);
	}

	public PanelWI getPanel() {
		return (PanelWI) this.source;
	}

}
