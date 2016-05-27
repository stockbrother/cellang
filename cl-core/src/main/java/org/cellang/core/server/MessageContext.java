package org.cellang.core.server;

import org.cellang.commons.lang.Path;

public class MessageContext {

	private Path path;

	public MessageContext(Path path) {
		this.path = path;
	}

	public Path getPath() {
		return this.path;
	}

}
