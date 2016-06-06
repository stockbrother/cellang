package org.cellang.core.server;

import org.cellang.core.lang.MessageI;

public abstract class Channel {
	protected String id;

	public Channel(String id) {
		this.id = id;
	}

	public abstract void sendMessage(MessageI msg);

	public String getId() {
		return this.id;
	}
}
