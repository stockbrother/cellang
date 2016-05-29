/**
 * Jun 30, 2012
 */
package org.cellang.clwt.commons.client.widget;

import org.cellang.clwt.commons.client.mvc.widget.EditorI;
import org.cellang.clwt.core.client.event.Event;

/**
 * @author wu
 * 
 */
public class ChangeEvent extends Event {

	public static Event.Type<ChangeEvent> TYPE = new Event.Type<ChangeEvent>("change");

	/** */
	public ChangeEvent(EditorI source) {
		super(TYPE, source);
	}

	public EditorI getEditor() {
		return (EditorI) this.source;
	}

	public <T> T getData() {
		return (T) this.getEditor().getData();
	}

}
