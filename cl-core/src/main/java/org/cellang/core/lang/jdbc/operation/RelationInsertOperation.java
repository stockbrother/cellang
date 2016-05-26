package org.cellang.core.lang.jdbc.operation;

import org.cellang.core.commons.jdbc.ConnectionPoolWrapper;
import org.cellang.core.commons.jdbc.SqlOperation;
import org.cellang.core.lang.CellRecord;
import org.cellang.core.lang.jdbc.RelationsTable;

public class RelationInsertOperation extends SqlOperation<Void> {
	
	private static String SQL = "insert into " + RelationsTable.T_RELATIONS + "(id1,typeid,id2)values(?,?,?)"; 
	
	private Object[] parameters = new Object[3];
	public RelationInsertOperation(ConnectionPoolWrapper cpw) {
		super(cpw, SQL, 3);
	}

	@Override
	public Void execute() {
		this.poolWrapper.executeUpdate(this.sql,this.parameters);
		return null;
	}

	public RelationInsertOperation relation(String id1, char typeId,String id2) {
		//
		int i=0;
		this.parameters[i++] = id1;
		this.parameters[i++] = typeId;
		this.parameters[i++] = id2;
		
		return this;
	}
	
}
