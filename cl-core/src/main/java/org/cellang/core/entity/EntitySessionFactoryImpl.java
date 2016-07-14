package org.cellang.core.entity;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import org.cellang.commons.jdbc.ConnectionProvider;
import org.cellang.commons.jdbc.JdbcDataAccessTemplate;
import org.cellang.commons.jdbc.JdbcOperation;
import org.cellang.commons.util.UUIDUtil;
import org.cellang.core.h2db.H2ConnectionPoolWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EntitySessionFactoryImpl implements EntitySessionFactory {
	private static final Logger LOG = LoggerFactory.getLogger(EntitySessionFactoryImpl.class);

	private EntityConfigFactory entityConfigFactory = new EntityConfigFactory();

	private boolean isNew;

	private JdbcDataAccessTemplate dataAccessTemplate;
	private ConnectionProvider pool;

	public EntitySessionFactoryImpl(ConnectionProvider pool, EntityConfigFactory ecf, boolean isNew) {
		this.pool = pool;
		this.dataAccessTemplate = new JdbcDataAccessTemplate();
		this.isNew = isNew;

	}

	public static EntitySessionFactory newInstance(File dbHome, String dbName, EntityConfigFactory ecf) {
		File dbFile = new File(dbHome, dbName + ".mv.db");
		boolean isNew = !dbFile.exists();

		String dbUrl = "jdbc:h2:" + dbHome.getAbsolutePath().replace('\\', '/') + "/" + dbName;
		LOG.info("dbUrl:" + dbUrl);
		ConnectionProvider pool = H2ConnectionPoolWrapper.newInstance(dbUrl, "sa", "sa");
		JdbcDataAccessTemplate template = new JdbcDataAccessTemplate();
		EntitySessionFactoryImpl rt = new EntitySessionFactoryImpl(pool, ecf, isNew);

		if (!isNew) {
			LOG.warn("skip init,since db file exists:" + dbFile);//
		} else {
			LOG.warn("initializing db,db file not exists: " + dbFile);//
			// create tables
			EntitySession es = rt.openSession();
			try {
				rt.entityConfigFactory.initTables(es, template);
				rt.entityConfigFactory.initIndices(es, template);

				int year = 2015 - 1900;

				for (int i = 0; i < 10; i++) {
					DateInfoEntity di = new DateInfoEntity();
					di.setId(UUIDUtil.randomStringUUID());
					di.setValue(new Date(year--, 11, 31));
					es.save(di);
				}
			} finally {
				es.close(true);
			}
		}
		return rt;
	}

	public EntityConfigFactory getEntityConfigFactory() {
		return this.entityConfigFactory;
	}

	@Override
	public JdbcDataAccessTemplate getDataAccessTemplate() {
		return this.dataAccessTemplate;
	}

	public void close() {
		this.pool.dispose();
	}

	@Override
	public EntitySession openSession() {
		try {
			Connection c = this.pool.openConnection();
			if (c.getAutoCommit()) {
				throw new RuntimeException("auto commit not allowed.");
			}
			EntitySessionImpl rt = new EntitySessionImpl(c, this.entityConfigFactory, this);
			return rt;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public <T> T execute(EntityOp<T> op) {
		EntitySession es = this.openSession();
		try {
			T rt = null;
			try {
				rt = op.execute(es);//
			} catch (Throwable t) {

				es.rollback();
				if (t instanceof RuntimeException) {
					throw (RuntimeException) t;
				} else if (t instanceof Error) {
					throw (Error) t;
				} else {
					throw new RuntimeException(t);
				}
			}

			return rt;
		} finally {
			es.close(true);
		}
	}

	@Override
	public boolean isNew() {
		return isNew;
	}

	@Override
	public <T> T execute(JdbcOperation<T> op) {
		EntityOp<T> eop = new EntityOp<T>() {

			@Override
			public T execute(EntitySession es) {

				return es.execute(op);
			}
		};
		return this.execute(eop);
	}

}
