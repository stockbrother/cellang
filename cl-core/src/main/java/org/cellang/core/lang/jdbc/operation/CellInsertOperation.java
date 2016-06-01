package org.cellang.core.lang.jdbc.operation;

import java.util.UUID;

import org.cellang.commons.jdbc.ConnectionPoolWrapper;
import org.cellang.commons.jdbc.SqlOperation;
import org.cellang.core.lang.CellRecord;
import org.cellang.core.lang.jdbc.CellsTable;

public class CellInsertOperation extends SqlOperation<String> {

	private static String SQL = "insert into " + CellsTable.T_CELLS + "(id,typeid,value)values(?,?,?)";

	private Object[] parameters = new Object[3];

	public CellInsertOperation(ConnectionPoolWrapper cpw) {
		super(cpw, SQL, 3);
	}

	@Override
	public String execute() {
		String id = UUID.randomUUID().toString();
		this.parameters[0] = id;
		this.poolWrapper.executeUpdate(this.sql, this.parameters);
		return id;
	}

	public CellInsertOperation cell(CellRecord ao) {
		//

		int i = 1;
		this.parameters[i++] = ao.getTypeId();
		this.parameters[i++] = ao.getValue().getValue();

		return this;
	}

}