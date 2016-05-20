package com.sbrother.sbook.core.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.h2.jdbcx.JdbcConnectionPool;

import com.sbrother.sbook.common.SbConfiguration;
import com.sbrother.sbook.common.jdbc.SbConnectionPoolWrapper;
import com.sbrother.sbook.common.jdbc.SbResultSetProcessor;
import com.sbrother.sbook.core.SbAbstractCellSource;
import com.sbrother.sbook.core.SbCellValue;
import com.sbrother.sbook.core.SbValue;

public class SbJdbcCellSource extends SbAbstractCellSource {

	private JdbcConnectionPool pool;

	private String url;

	private SbConnectionPoolWrapper poolWrapper;

	public SbJdbcCellSource() {
	}

	@Override
	public SbCellValue getCell(String bookId, String typeId) {

		SbCellValue rt = new SbSelectCellSql().bookId(bookId).execute(this.poolWrapper, bookId, typeId);
		return rt;
	}

	@Override
	public void save(final SbCellValue ao) {

		this.poolWrapper.executeUpdate(SbCellTable.SQL_INSERT.getSql(),
				new Object[] { ao.getBookId(), ao.getTypeId(), ao.getValue().getValue() });
	}

	public void configure(SbConfiguration cfg) {
		url = cfg.getString("url", true);
	}

	@Override
	public void open() {
		pool = JdbcConnectionPool.create(this.url, "sa", "sa");
		this.poolWrapper = new SbConnectionPoolWrapper(this.pool);
		this.poolWrapper.executeUpdate(SbCellTable.SQL_CREATE.getSql());
	}

	@Override
	public void close() {
		pool.dispose();
	}

	@Override
	public void clear() {
		this.poolWrapper.executeUpdate(SbCellTable.SQL_DELETE.getSql());
	}

}
