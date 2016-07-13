package org.cellang.core.entity;

import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.cellang.commons.jdbc.ConnectionProvider;
import org.cellang.commons.jdbc.InsertRowOperation;
import org.cellang.commons.jdbc.JdbcDataAccessTemplate;
import org.cellang.commons.jdbc.JdbcOperation;
import org.cellang.commons.util.UUIDUtil;
import org.cellang.core.h2db.H2ConnectionPoolWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EntityService {

	private static final Logger LOG = LoggerFactory.getLogger(EntityService.class);

	private EntityConfigFactory entityConfigFactory = new EntityConfigFactory();

	private boolean isNew;

	private JdbcDataAccessTemplate dataAccessTemplate;

	private ConnectionProvider pool;

	private EntityService(ConnectionProvider rt, EntityConfigFactory ecf, boolean isNew) {
		this.pool = rt;
		this.entityConfigFactory = ecf;
		this.isNew = isNew;
		this.dataAccessTemplate = new JdbcDataAccessTemplate(rt);
	}

	public JdbcDataAccessTemplate getDataAccessTemplate() {
		return dataAccessTemplate;
	}

	public static EntityService newInstance(File dbHome, String dbName, EntityConfigFactory ecf) {
		File dbFile = new File(dbHome, dbName + ".mv.db");
		boolean isNew = !dbFile.exists();

		String dbUrl = "jdbc:h2:" + dbHome.getAbsolutePath().replace('\\', '/') + "/" + dbName;
		LOG.info("dbUrl:" + dbUrl);
		ConnectionProvider pool = H2ConnectionPoolWrapper.newInstance(dbUrl, "sa", "sa");
		JdbcDataAccessTemplate template = new JdbcDataAccessTemplate(pool);
		EntityService rt = new EntityService(pool, ecf, isNew);

		if (!isNew) {
			LOG.warn("skip init,since db file exists:" + dbFile);//
		} else {
			LOG.warn("initializing db,db file not exists: " + dbFile);//
			// create tables
			rt.entityConfigFactory.initTables(template);
			rt.entityConfigFactory.initIndices(template);
			int year = 2015 - 1900;
			for (int i = 0; i < 10; i++) {
				DateInfoEntity di = new DateInfoEntity();
				di.setId(UUIDUtil.randomStringUUID());
				di.setValue(new Date(year--, 11, 31));
				rt.save(di);
			}
		}
		return rt;
	}

	public void close() {
		// this.pool.close();
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
		return this.query(cls, fields, args).executeQuery();//
	}

	public <T extends EntityObject> EntityQuery<T> query(Class<T> cls, String field, Object arg) {
		return query(cls, new String[] { field }, new Object[] { arg });
	}

	public <T extends EntityObject> EntityQuery<T> query(Class<T> cls) {
		return query(cls, new String[] {}, new Object[] {});
	}

	public <T extends EntityObject> EntityQuery<T> query(Class<T> cls, String[] fields, Object[] args) {
		EntityQuery<T> eq = new EntityQuery<T>(this, cls, fields, args);
		return eq;
	}

	public void saveAll(List<EntityObject> seList) {
		JdbcOperation<Void> op = new JdbcOperation<Void>(this.dataAccessTemplate) {

			@Override
			public Void execute(Connection con) {
				for (EntityObject eo : seList) {

					Class cls = eo.getClass();
					EntityConfig ec = EntityService.this.entityConfigFactory.get(cls);
					InsertRowOperation insertOp = new InsertRowOperation(this.template, ec.getTableName());
					ec.fillInsert(eo, insertOp);
					insertOp.execute(con);
				}

				return null;
			}
		};

		op.execute();

	}

	public void save(EntityObject se) {
		List<EntityObject> ol = new ArrayList<EntityObject>();
		ol.add(se);
		saveAll(ol);
	}

	public <T extends EntityObject> long delete(Class<T> cls, String[] strings, Object[] objects) {
		String sql = "delete from " + this.entityConfigFactory.get(cls).getTableName() + " where 1=1 ";
		for (int i = 0; i < strings.length; i++) {

			sql += " and " + strings[i] + "=?";
		}
		String sqlF = sql;
		JdbcOperation<Long> op = new JdbcOperation<Long>(this.dataAccessTemplate) {

			@Override
			public Long execute(Connection con) {
				return this.template.executeUpdate(con, sqlF, objects);
			}
		};
		return op.execute();
	}

	public EntityConfigFactory getEntityConfigFactory() {
		return entityConfigFactory;
	}

	public boolean isNew() {
		return isNew;
	}

	/**
	 * Remove all data from db.
	 */
	public void clear() {
		new JdbcOperation<Long>(this.dataAccessTemplate) {

			@Override
			public Long execute(Connection con) {
				long rt = 0;
				for (EntityConfig ec : EntityService.this.entityConfigFactory.getEntityConfigList()) {
					String sql = "delete from " + ec.getTableName();

					long rtI = this.template.executeUpdate(con, sql);
					LOG.info("deleted all(" + rtI + ") record from table:" + ec.getTableName() + "");
					rt += rtI;
				}
				LOG.info("done of clear.");				
				return rt;
			}
		}.execute();

	}

}
