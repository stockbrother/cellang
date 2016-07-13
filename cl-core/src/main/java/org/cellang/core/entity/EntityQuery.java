package org.cellang.core.entity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.cellang.commons.jdbc.JdbcOperation;
import org.cellang.commons.jdbc.ResultSetProcessor;

public class EntityQuery<T extends EntityObject> {
	private EntityConfig entityConfig;
	private String[] queryFields;
	private Object[] queryArgs;
	private String[] orderBy;
	private EntityService es;
	private Integer limit;
	private Integer offset;

	public EntityQuery(EntityService es, Class<T> cls, String[] qfields, Object[] args) {
		this.es = es;
		this.entityConfig = es.getEntityConfigFactory().get(cls);//
		this.queryFields = qfields;
		this.queryArgs = args;
	}

	public List<T> executeQuery() {
		ResultSetProcessor rsp = new ResultSetProcessor() {

			@Override
			public Object process(ResultSet rs) throws SQLException {

				List<T> rt = new ArrayList<T>();
				while (rs.next()) {
					T rtI = (T) EntityQuery.this.entityConfig.extractFrom(rs);
					rt.add(rtI);
				}
				return rt;
			}
		};
		StringBuffer sql = new StringBuffer().append("select * from ").append(this.entityConfig.getTableName())
				.append(" where 1=1");
		for (int i = 0; i < queryFields.length; i++) {
			sql.append(" and " + queryFields[i] + "=?");
		}
		if (orderBy != null) {
			sql.append(" order by");
			for (int i = 0; i < this.orderBy.length; i++) {
				sql.append(" ").append(this.orderBy[i]);
				if (i < this.orderBy.length - 1) {
					sql.append(",");
				}
			}
		}
		if (this.limit != null) {
			sql.append(" limit ").append(this.limit);
		}

		if (this.offset != null) {
			sql.append(" offset ").append(this.offset);
		}

		JdbcOperation<List<T>> op = new JdbcOperation<List<T>>(es.getDataAccessTemplate()){

			@Override
			public List<T> execute(Connection con) {
				
				return (List<T>) es.getDataAccessTemplate().executeQuery(con,sql.toString(), EntityQuery.this.queryArgs, rsp);
			}};
		
		List<T> rt = op.execute();
		return rt;
	}

	public String[] getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String[] orderBy) {
		this.orderBy = orderBy;
	}

	public EntityQuery<T> limit(int limit) {
		this.limit = limit;
		return this;
	}

	public EntityQuery<T> offset(int offset) {
		this.offset = offset;
		return this;
	}

	public EntityQuery<T> orderBy(String[] strings) {
		//
		this.setOrderBy(strings);//
		return this;
	}

}
