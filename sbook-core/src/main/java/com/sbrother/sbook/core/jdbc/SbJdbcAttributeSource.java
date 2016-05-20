package com.sbrother.sbook.core.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.h2.jdbcx.JdbcConnectionPool;

import com.sbrother.sbook.common.SbConfiguration;
import com.sbrother.sbook.common.jdbc.SbConnectionPoolWrapper;
import com.sbrother.sbook.common.jdbc.SbResultSetProcessor;
import com.sbrother.sbook.core.SbAbstractCellSource;
import com.sbrother.sbook.core.SbDate;
import com.sbrother.sbook.core.SbCellList;
import com.sbrother.sbook.core.SbName;
import com.sbrother.sbook.core.SbCell;
import com.sbrother.sbook.core.SbOwner;
import com.sbrother.sbook.core.SbValue;

public class SbJdbcAttributeSource extends SbAbstractCellSource {

	private JdbcConnectionPool pool;

	private String url;

	private SbConnectionPoolWrapper poolWrapper;

	public SbJdbcAttributeSource() {
	}

	@Override
	public SbCellList getAttributeList(SbOwner ao, SbDate date) {

		return null;
	}

	@Override
	public SbCell getAttributeObject(SbOwner ao, SbDate date, SbName name) {
		SbCell rt = (SbCell) this.poolWrapper.executeQuery( //
				SbCellTable.SQL_SELECT, new Object[] { ao.getValue(), new Date(date.getValue()), name.getValue() }, //
				new SbResultSetProcessor() {

					@Override
					public Object process(ResultSet rs) throws SQLException {
						SbCell rt = null;
						while (rs.next()) {
							rt = new SbCell();
							rt.setDate(SbDate.getInstance(rs.getTimestamp("date").getTime()));
							rt.setName(SbName.getInstance(rs.getString("name")));
							rt.setOwner(SbOwner.getInstance(rs.getString("owner")));
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

		this.poolWrapper.executeUpdate(SbCellTable.SQL_INSERT, new Object[] { ao.getOwner().getValue(),
				new Date(ao.getDate().getValue()), ao.getName().getValue(), ao.getValue().getValue() });
	}

	public void configure(SbConfiguration cfg) {
		url = cfg.getString("url", true);
	}

	@Override
	public void open() {
		pool = JdbcConnectionPool.create(this.url, "sa", "sa");
		this.poolWrapper = new SbConnectionPoolWrapper(this.pool);
		this.poolWrapper.executeUpdate(SbCellTable.SQL_CREATE);
	}

	@Override
	public void close() {
		pool.dispose();
	}

	@Override
	public void clear() {
		this.poolWrapper.executeUpdate(SbCellTable.SQL_DELETE);
	}

}
