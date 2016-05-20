package com.sbrother.sbook.core.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.h2.jdbcx.JdbcConnectionPool;

import com.sbrother.sbook.common.SbConfiguration;
import com.sbrother.sbook.common.jdbc.SbConnectionPoolWrapper;
import com.sbrother.sbook.common.jdbc.SbResultSetProcessor;
import com.sbrother.sbook.core.SbAbstractCellSource;
import com.sbrother.sbook.core.SbCell;
import com.sbrother.sbook.core.SbValue;

public class SbJdbcAttributeSource extends SbAbstractCellSource {

	private JdbcConnectionPool pool;

	private String url;

	private SbConnectionPoolWrapper poolWrapper;

	public SbJdbcAttributeSource() {
	}

	@Override
	public SbCell getCell(String date, String name) {
		SbCell rt = (SbCell) this.poolWrapper.executeQuery( //
				SbCellTable.SQL_SELECT.getSql(), new Object[] { date, name }, //
				new SbResultSetProcessor() {

					@Override
					public Object process(ResultSet rs) throws SQLException {
						SbCell rt = null;
						while (rs.next()) {
							rt = new SbCell();
							rt.setBookIdentifier(rs.getString("book"));
							rt.setName(rs.getString("name"));
							rt.setValue(SbValue.getInstance(rs.getBigDecimal("value")));//
							break;
						}
						return rt;
					}
				});

		return rt;
	}

	@Override
	public void saveAttributeObject(final SbCell ao) {

		this.poolWrapper.executeUpdate(SbCellTable.SQL_INSERT.getSql(),
				new Object[] { ao.getBookIdentifier(), ao.getName(), ao.getValue().getValue() });
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
