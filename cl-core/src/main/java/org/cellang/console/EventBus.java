package org.cellang.console;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cellang.console.control.EventListener;

public class EventBus {

	private Map<Class, List<EventListener>> listenerMap = new HashMap<>();

	public void addEventListener(Class type, EventListener elistener) {
		List<EventListener> list = this.listenerMap.get(type);
		if (list == null) {
			list = new ArrayList<EventListener>();
			this.listenerMap.put(type, list);
		}

		list.add(elistener);
	}

	public void dispatch(Object evt) {

		Class type = evt.getClass();

		List<EventListener> list = this.listenerMap.get(type);
		if (list == null) {
			return;
		}

		for (EventListener el : list) {
			el.onEvent(evt);
		}
	}
}
