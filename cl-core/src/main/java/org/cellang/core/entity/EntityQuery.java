package org.cellang.core.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.cellang.commons.jdbc.ResultSetProcessor;

public class EntityQuery<T extends EntityObject> {
	private EntityConfig entityConfig;
	private String[] queryFields;
	private Object[] queryArgs;
	private String[] orderBy;
	private EntityService es;

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
		String sql = "select * from " + this.entityConfig.getTableName() + " where 1=1";
		for (int i = 0; i < queryFields.length; i++) {
			sql += " and " + queryFields[i] + "=?";
		}
		if (orderBy != null) {
			sql += " order by";
			for (int i = 0; i < this.orderBy.length; i++) {
				sql += " " + this.orderBy[i];
				if (i < this.orderBy.length - 1) {
					sql += ",";
				}
			}
		}

		List<T> rt = (List<T>) es.getPool().executeQuery(sql, this.queryArgs, rsp);
		return rt;
	}

	public String[] getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String[] orderBy) {
		this.orderBy = orderBy;
	}

	public EntityQuery<T> orderBy(String[] strings) {
		//
		this.setOrderBy(strings);//
		return this;
	}

}
