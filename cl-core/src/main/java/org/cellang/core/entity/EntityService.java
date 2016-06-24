package org.cellang.core.entity;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.cellang.commons.jdbc.ConnectionPoolWrapper;
import org.cellang.commons.jdbc.CreateTableOperation;
import org.cellang.commons.jdbc.InsertRowOperation;
import org.cellang.commons.jdbc.ResultSetProcessor;
import org.cellang.core.h2db.H2ConnectionPoolWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EntityService {

	private static final Logger LOG = LoggerFactory.getLogger(EntityService.class);

	private ConnectionPoolWrapper pool;
	private static String SELECT_ACCOUNT_BY_EMAIL = "select * from " + AccountEntity.tableName + " where email = ?";

	private EntityService(ConnectionPoolWrapper rt) {
		this.pool = rt;
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
		CreateTableOperation cto = new CreateTableOperation(this.pool, AccountEntity.tableName);
		AccountEntity.fillCreate(cto);
		cto.execute();
	}

	public void close() {
		this.pool.close();
	}

	public AccountEntity getAccount(String id) {

		ResultSetProcessor rsp = new ResultSetProcessor() {

			@Override
			public Object process(ResultSet rs) throws SQLException {
				AccountEntity rt = null;
				while (rs.next()) {
					rt = new AccountEntity();
					rt.extractFrom(rs);
					break;
				}
				return rt;
			}
		};
		AccountEntity rt = (AccountEntity) pool.executeQuery(SELECT_ACCOUNT_BY_EMAIL, new Object[] { id }, rsp);
		return rt;
	}

	public void save(AccountEntity an) {
		InsertRowOperation insertOp = new InsertRowOperation(this.pool, AccountEntity.tableName);
		an.fillInsert(insertOp);
		insertOp.execute();
	}

}
