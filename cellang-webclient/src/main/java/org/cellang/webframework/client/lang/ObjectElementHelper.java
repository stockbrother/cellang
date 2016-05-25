/**
 * Jun 30, 2012
 */
package org.cellang.webframework.client.lang;

import org.cellang.webframework.client.core.ElementWrapper;
import org.cellang.webframework.client.gwtbridge.GwtHandlerI;

import com.google.gwt.event.dom.client.DomEvent;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;

/**
 * @author wu
 *         <p>
 * 
 */
public class ObjectElementHelper extends ElementWrapper implements EventListener {

	protected HandlerManager target;

	protected int eventsToSink;

	protected WebObject owner;

	protected boolean attching;

	public ObjectElementHelper(Element ele, WebObject owner) {
		super(ele);
		this.owner = owner;
		this.target = new HandlerManager(ele);//
	}

	@Override
	public void onBrowserEvent(Event event) {
		switch (DOM.eventGetType(event)) {
		case Event.ONMOUSEOVER:
			// Only fire the mouse over event if it's coming from outside this
			// widget.
		case Event.ONMOUSEOUT:
			// Only fire the mouse out event if it's leaving this
			// widget.
			Element related = event.getRelatedEventTarget().cast();
			if (related != null && this.element.isOrHasChild(related)) {
				return;
			}
			break;
		}
		com.google.gwt.event.dom.client.DomEvent.fireNativeEvent(event,
				this.target, this.element);
	}

	public <E extends GwtEvent<H>, H extends EventHandler> HandlerRegistration addGwtHandler(
			DomEvent.Type<H> type, GwtHandlerI<E, H> eh) {
		return addHandler(type, eh.cast());
	}

	@Deprecated
	protected <H extends EventHandler> HandlerRegistration addHandler(
			DomEvent.Type<H> type, final H handler) {
		int bit = Event.getTypeInt(type.getName());
		this.sinkEvents(bit);
		return this.target.addHandler(type, handler);

	}

	protected void sinkEvents(int eventBitsToAdd) {
		// if attaching,means call by attach from widget
		// if attached,may call by addGwtHandler();
		if (this.attching || this.owner.isAttached()) {// attaching,or the
														// widget is attached.
			DOM.sinkEvents(element, eventBitsToAdd | DOM.getEventsSunk(element));
		} else {// save value,wait attach
			this.eventsToSink |= eventBitsToAdd;
		}
	}

	// attach calling by the widget which is not attached,but is attaching.
	public void attach() {
		this.attching = true;
		try {
			DOM.setEventListener(element, this);
			int bitsToAdd = eventsToSink;
			eventsToSink = -1;
			if (bitsToAdd > 0) {
				sinkEvents(bitsToAdd);
			}
		} finally {
			this.attching = false;
		}

	}

	public void detach() {
		DOM.setEventListener(element, null);
	}

}
