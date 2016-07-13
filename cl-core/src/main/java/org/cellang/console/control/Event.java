package org.cellang.console.control;

import java.util.HashMap;

public class Event extends HashMap<String, Object> {
	private int type;

	public Event(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}

}
