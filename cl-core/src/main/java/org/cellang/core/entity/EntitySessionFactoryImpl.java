
package org.cellang.core.entity;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cellang.commons.jdbc.ConnectionProvider;
import org.cellang.commons.jdbc.JdbcDataAccessTemplate;
import org.cellang.commons.jdbc.JdbcOperation;
import org.cellang.core.h2db.H2ConnectionPoolWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EntitySessionFactoryImpl implements EntitySessionFactory {
	private static final Logger LOG = LoggerFactory.getLogger(EntitySessionFactoryImpl.class);

	private EntityConfigFactory entityConfigFactory = new EntityConfigFactory();

	DataVersion dataVersion;
	private JdbcDataAccessTemplate dataAccessTemplate;
	private ConnectionProvider pool;
	private DataVersion targetDataVersion = DataVersion.V_latest;

	JdbcDataAccessTemplate template = new JdbcDataAccessTemplate();

	List<DBUpgrader> upgraderList = new ArrayList<DBUpgrader>();

	public EntitySessionFactoryImpl(ConnectionProvider pool, EntityConfigFactory ecf, DataVersion dv) {
		this.pool = pool;
		this.dataAccessTemplate = new JdbcDataAccessTemplate();
		this.dataVersion = dv;
		upgraderList.add(new V0_0_2DBUpgrader());
		upgraderList.add(new V0_0_3DBUpgrader());
		upgraderList.add(new V0_0_4DBUpgrader());
		upgraderList.add(new V0_0_5DBUpgrader());
		upgraderList.add(new V0_0_6DBUpgrader());
		upgraderList.add(new V0_0_7DBUpgrader());
		upgraderList.add(new V0_0_8DBUpgrader());		
	}

	public static EntitySessionFactory newInstance(File dbHome, String dbName, EntityConfigFactory ecf) {

		String dbUrl = "jdbc:h2:" + dbHome.getAbsolutePath().replace('\\', '/') + "/" + dbName;
		LOG.info("dbUrl:" + dbUrl);

		ConnectionProvider pool = H2ConnectionPoolWrapper.newInstance(dbUrl, "sa", "sa");

		DataVersion dv = DataVersion.V_UNKNOW;// to be resovled later.

		EntitySessionFactoryImpl rt = new EntitySessionFactoryImpl(pool, ecf, dv);
		EntitySession es = rt.openSession();
		try {
			rt.dataVersion = rt.resovleDataVersion(es);
		} finally {
			es.close(true);
		}
		rt.upgrade();
		return rt;
	}

	private void upgrade() {
		while (true) {
			if (this.dataVersion == this.targetDataVersion) {
				break;
			}
			
			DataVersion pre = this.dataVersion;
			DataVersion dv = this.tryUpgrade();
			if (dv == null) {
				LOG.warn("cannot upgrade from:" + pre + " to target:" + this.targetDataVersion);
				break;
			}
			LOG.info("successfuly upgrade from:" + pre + " to target:" + dv);
		}
	}

	private DataVersion tryUpgrade() {
		DataVersion rt = null;
		for (DBUpgrader up : this.upgraderList) {
			if (this.dataVersion == up.getSourceVersion()) {
				up.upgrade(this);//
				rt = up.getTargetVersion();
			}
		}
		if (rt != null) {
			this.dataVersion = rt;
		}
		return rt;
	}

	private DataVersion resovleDataVersion(EntitySession es) {
		return es.execute(new JdbcOperation<DataVersion>() {

			@Override
			protected DataVersion doExecute(Connection con) {

				return resolveDataVersion(es, con);
			}
		});

	}

	private DataVersion resolveDataVersion(EntitySession es, Connection con) {
		DataVersion rt = null;
		if (this.isTableExists(con, PropertyEntity.tableName)) {
			Map<String, Map<String, String>> mapmap = new HashMap<>();
			List<PropertyEntity> pL = es.query(PropertyEntity.class).execute(es);
			for (PropertyEntity pe : pL) {
				String category = pe.getCategory();
				String key = pe.getKey();
				Map<String, String> map = mapmap.get(category);
				if (map == null) {
					map = new HashMap<>();
					mapmap.put(category, map);
				}
				String value = pe.getValue();
				map.put(key, value);
			}
			if (mapmap.isEmpty()) {
				// table property is empty.
				rt = DataVersion.V_0_0_3;
			} else {
				// read version number from db.
				Map<String, String> coreMap = mapmap.get("core");

				String value = coreMap.get("data-version");

				rt = DataVersion.valueOf(value);
			}
		} else {
			if (this.isTableExists(con, BalanceSheetReportEntity.tableNameV001)) {
				rt = DataVersion.V_0_0_1;
			} else {
				rt = DataVersion.V_0_0_2;
			}
		}
		return rt;

	}

	private boolean isTableExists(Connection con, String tableName) {
		// table_schema
		String sql = "select * from information_schema.tables where table_name=?";
		List<Object[]> ll = this.template.executeQuery(con, sql, tableName.toUpperCase());
		if (ll.isEmpty()) {
			return false;
		} else {
			return true;
		}
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
	public DataVersion getDataVersion() {
		return this.dataVersion;
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