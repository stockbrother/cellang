package org.cellang.core.jdbc.operation;

import org.cellang.common.jdbc.ConnectionPoolWrapper;
import org.cellang.common.jdbc.SqlOperation;
import org.cellang.core.CellRecord;
import org.cellang.core.jdbc.CellsTable;

public class ClearDBOperation extends SqlOperation<Void> {
	
	private static String SQL = "delete from " + CellsTable.T_CELLS;
	
	public ClearDBOperation(ConnectionPoolWrapper cpw) {
		super(cpw, SQL, 0);
	}

	@Override
	public Void execute() {
		this.poolWrapper.executeUpdate(this.sql);
		return null;
	}
	
}
