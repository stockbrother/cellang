package com.sbrother.sbook.core.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.sbrother.sbook.common.jdbc.SbConnectionPoolWrapper;
import com.sbrother.sbook.common.jdbc.SbResultSetProcessor;
import com.sbrother.sbook.common.jdbc.SbSqlOperation;
import com.sbrother.sbook.core.SbCellValue;
import com.sbrother.sbook.core.SbValue;

public class SbSelectCellSql extends SbSqlOperation<SbCellValue> {

	private String bookId;
	private String typeId;

	public SbSelectCellSql(SbConnectionPoolWrapper cpw) {
		super(cpw, "select bookid,typeid,value from " + SbCellTable.T_CELLS + " where bookid=? and typeid=?", 2);
	}

	@Override
	public SbCellValue execute() {
		return (SbCellValue) poolWrapper.executeQuery( //
				this.getSql(), new Object[] { bookId, typeId }, //
				new SbResultSetProcessor() {

					@Override
					public Object process(ResultSet rs) throws SQLException {
						SbCellValue rt = null;
						while (rs.next()) {
							rt = new SbCellValue();
							rt.setBookId(rs.getString("bookid"));
							rt.setTypeId(rs.getString("typeid"));
							rt.setValue(SbValue.getInstance(rs.getBigDecimal("value")));//
							break;
						}
						return rt;
					}
				});
	}

	public String getBookId() {
		return bookId;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

}
