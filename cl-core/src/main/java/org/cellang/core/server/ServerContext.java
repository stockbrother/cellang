package org.cellang.core.server;

import org.cellang.core.lang.CellSource;

public class ServerContext {
	CellSource cellSource;

	public ServerContext() {

	}

	public CellSource getCellSource() {
		//
		return this.cellSource;
	}

	public void setCellSource(CellSource cellSource) {
		this.cellSource = cellSource;
	}

}
