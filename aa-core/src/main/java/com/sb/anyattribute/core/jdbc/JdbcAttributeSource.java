package com.sb.anyattribute.core.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.h2.jdbcx.JdbcConnectionPool;

import com.sb.anyattribute.common.Configuration;
import com.sb.anyattribute.core.AbstractAttributeSource;
import com.sb.anyattribute.core.AttributeDate;
import com.sb.anyattribute.core.AttributeList;
import com.sb.anyattribute.core.AttributeName;
import com.sb.anyattribute.core.AttributeObject;
import com.sb.anyattribute.core.AttributeOwner;
import com.sb.anyattribute.core.AttributeValue;

public class JdbcAttributeSource extends AbstractAttributeSource {

	private JdbcConnectionPool pool;

	private String url;

	public JdbcAttributeSource() {
	}

	@Override
	public AttributeList getAttributeList(AttributeOwner ao, AttributeDate date) {

		return null;
	}

	@Override
	public AttributeObject getAttributeObject(AttributeOwner ao, AttributeDate date, AttributeName name) {
		AttributeObject rt = (AttributeObject) AttributeTable.executeQuery(this.pool, //
				AttributeTable.SQL_SELECT, new Object[] { ao.getValue(), new Date(date.getValue()), name.getValue() }, //
				new AttributeTable.ResultSetProcessor() {

					@Override
					public Object process(ResultSet rs) throws SQLException {
						AttributeObject rt = null;
						while (rs.next()) {
							rt = new AttributeObject();
							rt.setDate(AttributeDate.getInstance(rs.getTimestamp("date").getTime()));
							rt.setName(AttributeName.getInstance(rs.getString("name")));
							rt.setOwner(AttributeOwner.getInstance(rs.getString("owner")));
							rt.setValue(AttributeValue.getInstance(rs.getBigDecimal("value")));//
							break;
						}
						return rt;
					}
				});

		return rt;
	}

	@Override
	public void saveAttributeObject(final AttributeObject ao) {

		AttributeTable.executeUpdate(this.pool, AttributeTable.SQL_INSERT, new Object[] { ao.getOwner().getValue(),
				new Date(ao.getDate().getValue()), ao.getName().getValue(), ao.getValue().getValue() });
	}

	public void configure(Configuration cfg) {
		url = cfg.getString("url", true);
	}

	@Override
	public void open() {
		pool = JdbcConnectionPool.create(this.url, "sa", "sa");
		AttributeTable.createIfNotExist(this.pool);
	}

	@Override
	public void close() {
		pool.dispose();
	}

}
