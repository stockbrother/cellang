package org.cellang.core.lang.jdbc.operation;

import org.cellang.core.common.jdbc.ConnectionPoolWrapper;
import org.cellang.core.common.jdbc.SqlOperation;
import org.cellang.core.lang.CellRecord;
import org.cellang.core.lang.jdbc.CellsTable;

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
