package org.cellang.console.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventBus {
	private Map<Integer, List<EventListener>> listenerMap = new HashMap<Integer, List<EventListener>>();

	public void addEventListener(int type, EventListener elistener) {
		List<EventListener> list = this.listenerMap.get(type);
		if (list == null) {
			list = new ArrayList<EventListener>();
			this.listenerMap.put(type, list);
		}

		list.add(elistener);
	}

	public void dispatch(Event evt) {
		List<EventListener> list = this.listenerMap.get(evt.getType());
		if (list == null) {
			return;
		}

		for (EventListener el : list) {
			el.onEvent(evt);
		}
	}
}
