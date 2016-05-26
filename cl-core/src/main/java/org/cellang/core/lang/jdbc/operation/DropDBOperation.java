package org.cellang.core.lang.jdbc.operation;

import org.cellang.core.commons.jdbc.ConnectionPoolWrapper;
import org.cellang.core.commons.jdbc.JdbcOperation;
import org.cellang.core.lang.jdbc.CellsTable;
import org.cellang.core.lang.jdbc.PropertiesTable;
import org.cellang.core.lang.jdbc.RelationsTable;

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
