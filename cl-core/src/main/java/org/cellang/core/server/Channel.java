package org.cellang.core.server;

import org.cellang.core.lang.MessageI;

public abstract class Channel {
	public abstract void sendMessage(MessageI msg);
}
