package org.cellang.core.entity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.cellang.commons.jdbc.JdbcOperation;
import org.cellang.commons.jdbc.ResultSetProcessor;

/**
 * Utility, for ease of query operation.
 * 
 * @author wu
 *
 * @param <T>
 */
public class EntityQuery<T extends EntityObject> extends EntityOp<List<T>> {
	private static class WhereField {
		public WhereField(String field, String oper, Object value) {
			this.field = field;
			this.operator = oper;
			this.value = value;
		}

		private String field;
		private Object value;
		private String operator;
	}

	private EntityConfig entityConfig;

	private List<WhereField> whereFields = new ArrayList<>();

	private String[] orderBy;
	private Integer limit;
	private Integer offset;

	public EntityQuery(EntityConfigFactory ecf, Class<T> cls, String[] qfields, Object[] args) {
		this(ecf.get(cls), qfields, args);
	}

	public EntityQuery(EntityConfig ec) {
		this(ec, new String[] {}, new Object[] {});
	}

	public EntityQuery(EntityConfig ec, String[] qfields, Object[] args) {

		this.entityConfig = ec;//
		for (int i = 0; i < qfields.length; i++) {
			this.whereFields.add(new WhereField(qfields[i], "=", args[i]));
		}

	}

	@Override
	public List<T> execute(EntitySession es) {
		ResultSetProcessor<List<T>> rsp = new ResultSetProcessor<List<T>>() {

			@Override
			public List<T> process(ResultSet rs) throws SQLException {

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
		// equal query fields
		List<Object> args = new ArrayList<Object>();

		for (int i = 0; i < this.whereFields.size(); i++) {
			WhereField wf = this.whereFields.get(i);
			sql.append(" and " + wf.field + " " + wf.operator + " ?");
			args.add(wf.value);
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

		JdbcOperation<List<T>> op = new JdbcOperation<List<T>>() {

			@Override
			public List<T> doExecute(Connection con) {

				return (List<T>) template.executeQuery(con, sql.toString(), args, rsp);
			}
		};

		List<T> rt = es.execute(op);
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

	public EntityQuery<T> like(Map<String, String> likeMap) {
		for (Map.Entry<String, String> en : likeMap.entrySet()) {
			this.whereFields.add(new WhereField(en.getKey(), "like", en.getValue()));
		}
		return this;
	}

}
