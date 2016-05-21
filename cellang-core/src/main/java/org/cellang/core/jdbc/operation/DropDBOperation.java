package org.cellang.core.jdbc.operation;

import org.cellang.common.jdbc.ConnectionPoolWrapper;
import org.cellang.common.jdbc.JdbcOperation;
import org.cellang.core.jdbc.CellsTable;
import org.cellang.core.jdbc.PropertiesTable;
import org.cellang.core.jdbc.RelationsTable;

public class DropDBOperation extends JdbcOperation<Void> {

	private static String SQL0 = "drop all objects";

	public DropDBOperation(ConnectionPoolWrapper cpw) {
		super(cpw);
	}

	@Override
	public Void execute() {
		this.poolWrapper.executeUpdate(SQL0);

		return null;
	}

}
