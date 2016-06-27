package org.cellang.core.entity;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.cellang.commons.jdbc.ConnectionPoolWrapper;
import org.cellang.commons.jdbc.CreateTableOperation;
import org.cellang.commons.jdbc.InsertRowOperation;
import org.cellang.commons.jdbc.ResultSetProcessor;
import org.cellang.commons.util.UUIDUtil;
import org.cellang.core.h2db.H2ConnectionPoolWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EntityService {

	private static final Logger LOG = LoggerFactory.getLogger(EntityService.class);

	private ConnectionPoolWrapper pool;

	private EntityConfigFactory entityConfigFactory = new EntityConfigFactory();

	private EntityService(ConnectionPoolWrapper rt,EntityConfigFactory ecf) {
		this.pool = rt;
		this.entityConfigFactory = ecf;
		

	}
	
	public ConnectionPoolWrapper getPool() {
		return pool;
	}

	public static EntityService newInstance(File dbHome, String dbName,EntityConfigFactory ecf) {
		File dbFile = new File(dbHome, dbName + ".mv.db");
		boolean dbExists = dbFile.exists();

		String dbUrl = "jdbc:h2:" + dbHome.getAbsolutePath().replace('\\', '/') + "/" + dbName;
		LOG.info("dbUrl:" + dbUrl);
		ConnectionPoolWrapper pool = H2ConnectionPoolWrapper.newInstance(dbUrl, "sa", "sa");
		EntityService rt = new EntityService(pool,ecf);

		if (dbExists) {
			LOG.warn("db file exists:" + dbFile);//
		} else {
			LOG.warn("initializing db,db file not exists: " + dbFile);//
			// create tables
			rt.entityConfigFactory.initTables(pool);

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
		this.pool.close();
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

	public <T> List<T> getList(Class cls) {
		return getList(cls, new String[] {}, new Object[] {});
	}

	public <T> List<T> getList(Class cls, String field, Object value) {
		return this.getList(cls, new String[] { field }, new Object[] { value });
	}

	public <T> List<T> getList(Class cls, String[] fields, Object[] args) {
		EntityConfig ec = this.entityConfigFactory.get(cls);

		ResultSetProcessor rsp = new ResultSetProcessor() {

			@Override
			public Object process(ResultSet rs) throws SQLException {

				List<T> rt = new ArrayList<T>();
				while (rs.next()) {
					T rtI = (T) ec.extractFrom(rs);
					rt.add(rtI);
				}
				return rt;
			}
		};
		String sql = "select * from " + ec.getTableName() + " where 1=1";
		for (int i = 0; i < fields.length; i++) {
			sql += " and " + fields[i] + "=?";
		}
		List<T> rt = (List<T>) pool.executeQuery(sql, args, rsp);
		return rt;
	}

	public void save(EntityObject se) {
		Class cls = se.getClass();
		EntityConfig ec = this.entityConfigFactory.get(cls);
		InsertRowOperation insertOp = new InsertRowOperation(this.pool, ec.getTableName());
		ec.fillInsert(se, insertOp);
		insertOp.execute();
	}

	public <T extends EntityObject> long delete(Class<T> cls, String[] strings, Object[] objects) {
		String sql = "delete from " + this.entityConfigFactory.get(cls).getTableName() + " where 1=1 ";
		for (int i = 0; i < strings.length; i++) {

			sql += " and " + strings[i] + "=?";
		}
		return this.pool.executeUpdate(sql, objects);
	}

}
