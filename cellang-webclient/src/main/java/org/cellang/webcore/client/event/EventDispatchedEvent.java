/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 23, 2012
 */
package org.cellang.webcore.client.event;

/**
 * @author wu
 * 
 */
public class EventDispatchedEvent extends Event {

	public static final Type<EventDispatchedEvent> TYPE = new Type<EventDispatchedEvent>("dispatched");

	protected Event event;
	/**
	 * @param type
	 */
	public EventDispatchedEvent(Event event) {
	
		super(TYPE,event.getSource());
		this.event = event;
	}
	/**
	 * @return the event
	 */
	public Event getEvent() {
		return event;
	}

}
