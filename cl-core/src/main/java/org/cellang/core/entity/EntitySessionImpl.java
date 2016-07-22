package org.cellang.core.entity;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.cellang.commons.jdbc.InsertRowOperation;
import org.cellang.commons.jdbc.JdbcDataAccessTemplate;
import org.cellang.commons.jdbc.JdbcOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EntitySessionImpl implements EntitySession {

	private static final Logger LOG = LoggerFactory.getLogger(EntitySessionImpl.class);

	private Connection con;
	EntitySessionFactoryImpl esf;
	EntityConfigFactory ecf;
	JdbcDataAccessTemplate template;

	public EntitySessionImpl(Connection con, EntityConfigFactory ecf, EntitySessionFactoryImpl esf) {
		this.con = con;
		this.esf = esf;
		this.ecf = ecf;
		this.template = esf.getDataAccessTemplate();

	}

	public <T> T getSingle(Class cls, String field, Object arg) {
		return getSingle(cls, new String[] { field }, new Object[] { arg });
	}

	public <T> T getSingle(Class cls, String[] fields, Object[] args) {
		List<T> rtL = this.getList(cls, fields, args);
		if (rtL.isEmpty()) {
			return null;
		} else if (rtL.size() > 1) {
			throw new RuntimeException("too many:" + rtL.size());
		}
		return rtL.get(0);
	}

	public <T extends EntityObject> List<T> getList(Class<T> cls) {
		return getList(cls, new String[] {}, new Object[] {});
	}

	public <T extends EntityObject> List<T> getList(Class<T> cls, String field, Object value) {
		return this.getList(cls, new String[] { field }, new Object[] { value });
	}

	public <T extends EntityObject> List<T> getList(Class<T> cls, String[] fields, Object[] args) {
		return this.query(cls, fields, args).execute(this);//
	}

	public <T extends EntityObject> EntityQuery<T> query(Class<T> cls, String field, Object arg) {
		return query(cls, new String[] { field }, new Object[] { arg });
	}

	public <T extends EntityObject> EntityQuery<T> query(Class<T> cls) {
		return query(cls, new String[] {}, new Object[] {});
	}

	public <T extends EntityObject> EntityQuery<T> query(Class<T> cls, String[] fields, Object[] args) {
		EntityQuery<T> eq = new EntityQuery<T>(this.getEntityConfigFactory(), cls, fields, args);
		return eq;
	}

	public void saveAll(List<EntityObject> seList) {
		saveAll(seList.iterator());
	}

	public void saveAll(Iterator<EntityObject> eoIt) {

		while (eoIt.hasNext()) {
			EntityObject eo = eoIt.next();
			Class cls = eo.getClass();
			EntityConfig ec = EntitySessionImpl.this.ecf.get(cls);
			InsertRowOperation insertOp = new InsertRowOperation(ec.getTableName());
			ec.fillInsert(eo, insertOp);
			insertOp.doExecute(con);
		}

	}

	public void save(EntityObject se) {
		List<EntityObject> ol = new ArrayList<EntityObject>();
		ol.add(se);
		saveAll(ol);
	}

	@Override
	public <T extends EntityObject> long delete(Class<T> cls) {
		return delete(cls, new String[] {}, new Object[] {});
	}

	@Override
	public <T extends EntityObject> long delete(Class<T> cls, String[] strings, Object[] objects) {
		String sql = "delete from " + this.ecf.get(cls).getTableName() + " where 1=1 ";
		for (int i = 0; i < strings.length; i++) {

			sql += " and " + strings[i] + "=?";
		}
		String sqlF = sql;
		JdbcOperation<Long> op = new JdbcOperation<Long>() {

			@Override
			public Long doExecute(Connection con) {
				return this.template.executeUpdate(con, sqlF, objects);
			}
		};

		return op.execute(con);
	}

	public EntityConfigFactory getEntityConfigFactory() {
		return this.ecf;
	}

	/**
	 * Remove all data from db.
	 */
	public void clear() {
		new JdbcOperation<Long>() {

			@Override
			public Long doExecute(Connection con) {
				long rt = 0;
				for (EntityConfig ec : EntitySessionImpl.this.ecf.getEntityConfigList()) {
					String sql = "delete from " + ec.getTableName();

					long rtI = this.template.executeUpdate(con, sql);
					LOG.info("deleted all(" + rtI + ") record from table:" + ec.getTableName() + "");
					rt += rtI;
				}
				LOG.info("done of clear.");
				return rt;
			}
		}.doExecute(this.con);

	}

	@Override
	public void close(boolean commit) {

		try {
			if (commit) {
				this.commit();
			}
			this.con.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public EntitySessionFactory getFactory() {
		return esf;
	}

	@Override
	public <T> T execute(JdbcOperation<T> op) {
		return op.execute(this.con);
	}

	@Override
	public void rollback() {
		try {
			this.con.rollback();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void commit() {
		try {
			this.con.commit();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Connection getConnection() {
		return this.con;
	}

	@Override
	public JdbcDataAccessTemplate getDataAccessTemplate() {
		return this.template;
	}

}
