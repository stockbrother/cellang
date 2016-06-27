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

	private EntityService(ConnectionPoolWrapper rt) {
		this.pool = rt;
		this.entityConfigFactory.addEntity(new EntityConfig(AccountEntity.class, AccountEntity.tableName));
		this.entityConfigFactory.addEntity(new EntityConfig(BalanceItemEntity.class, BalanceItemEntity.tableName));
		this.entityConfigFactory.addEntity(new EntityConfig(BalanceSheetEntity.class, BalanceSheetEntity.tableName));
		this.entityConfigFactory.addEntity(new EntityConfig(CorpInfoEntity.class, CorpInfoEntity.tableName));
		this.entityConfigFactory.addEntity(new EntityConfig(DateInfoEntity.class, DateInfoEntity.tableName));
	}

	public ConnectionPoolWrapper getPool() {
		return pool;
	}

	public static EntityService newInstance(File dbHome, String dbName) {
		File dbFile = new File(dbHome, dbName + ".mv.db");
		boolean dbExists = dbFile.exists();

		String dbUrl = "jdbc:h2:" + dbHome.getAbsolutePath().replace('\\', '/') + "/" + dbName;
		LOG.info("dbUrl:" + dbUrl);
		ConnectionPoolWrapper pool = H2ConnectionPoolWrapper.newInstance(dbUrl, "sa", "sa");
		EntityService rt = new EntityService(pool);

		if (dbExists) {
			LOG.warn("db file exists:" + dbFile);//
		} else {
			LOG.warn("initializing db,db file not exists: " + dbFile);//
			rt.initilizeDB();
		}
		return rt;
	}

	private void initilizeDB() {
		// check if need to create table.

		// create tables
		{

			CreateTableOperation cto = new CreateTableOperation(this.pool, DateInfoEntity.tableName);
			DateInfoEntity.fillCreate(cto);
			cto.execute();
			int year = 2015 - 1900;
			for (int i = 0; i < 10; i++) {
				DateInfoEntity di = new DateInfoEntity();
				di.setId(UUIDUtil.randomStringUUID());
				di.setValue(new Date(year--, 11, 31));
				this.save(di);
			}
		}
		{

			CreateTableOperation cto = new CreateTableOperation(this.pool, CorpInfoEntity.tableName);
			CorpInfoEntity.fillCreate(cto);
			cto.execute();
		}
		{

			CreateTableOperation cto = new CreateTableOperation(this.pool, AccountEntity.tableName);
			AccountEntity.fillCreate(cto);
			cto.execute();
		}
		{

			CreateTableOperation cto = new CreateTableOperation(this.pool, BalanceItemEntity.tableName);
			BalanceItemEntity.fillCreate(cto);
			cto.execute();
		}
		{

			CreateTableOperation cto = new CreateTableOperation(this.pool, BalanceSheetEntity.tableName);
			BalanceSheetEntity.fillCreate(cto);
			cto.execute();
		}

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

}
