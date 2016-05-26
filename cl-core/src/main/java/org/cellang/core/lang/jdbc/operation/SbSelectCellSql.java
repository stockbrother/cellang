package org.cellang.core.lang.jdbc.operation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.cellang.core.common.jdbc.ConnectionPoolWrapper;
import org.cellang.core.common.jdbc.ResultSetProcessor;
import org.cellang.core.common.jdbc.SqlOperation;
import org.cellang.core.lang.CellRecord;
import org.cellang.core.lang.CellValue;
import org.cellang.core.lang.jdbc.CellsTable;
import org.cellang.core.lang.jdbc.RelationsTable;

public class SbSelectCellSql extends SqlOperation<List<CellRecord>> {

	private String parentId;
	private String typeId;

	public SbSelectCellSql(ConnectionPoolWrapper cpw) {
		super(cpw, "select c.id,c.typeid,c.value from " + CellsTable.T_CELLS + " c," + RelationsTable.T_RELATIONS + " r "
				+ " where r.id1=? and r.typeid=? and r.id2=c.id and c.typeid=?", 2);
	}

	@Override
	public List<CellRecord> execute() {
		return (List<CellRecord>) poolWrapper.executeQuery( //
				this.getSql(), new Object[] { parentId, RelationsTable.FV_CHILD, typeId }, //
				new ResultSetProcessor() {

					@Override
					public Object process(ResultSet rs) throws SQLException {
						List<CellRecord> rt = new ArrayList<CellRecord>();
						while (rs.next()) {
							CellRecord cI = new CellRecord();
							cI.setId(rs.getString("id"));
							cI.setTypeId(rs.getString("typeid"));
							cI.setValue(CellValue.getInstance(rs.getBigDecimal("value")));//
							rt.add(cI);
						}
						return rt;
					}
				});
	}

	public String getBookId() {
		return parentId;
	}

	public String getTypeId() {
		return typeId;
	}

	public SbSelectCellSql parentId(String bookId) {
		this.parentId = bookId;
		return this;
	}

	public SbSelectCellSql typeId(String typeId) {
		this.typeId = typeId;
		return this;
	}

}
