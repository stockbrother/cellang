package org.cellang.core.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cellang.commons.jdbc.ConnectionPoolWrapper;
import org.cellang.commons.jdbc.CreateTableOperation;

public class EntityConfigFactory {

	private List<EntityConfig> entityConfigList = new ArrayList<EntityConfig>();

	private Map<Class, EntityConfig> entityConfigMap = new HashMap<Class, EntityConfig>();
	private Map<Class, List<IndexConfig>> indexConfigListMap = new HashMap<Class, List<IndexConfig>>();

	public EntityConfigFactory() {
		this.addEntity(new EntityConfig(AccountEntity.class, AccountEntity.tableName));
		this.addEntity(new EntityConfig(BalanceSheetItemEntity.class, BalanceSheetItemEntity.tableName));
		this.addEntity(new EntityConfig(BalanceSheetReportEntity.class, BalanceSheetReportEntity.tableName));
		this.addEntity(new EntityConfig(IncomeStatementReportEntity.class, IncomeStatementReportEntity.tableName));
		this.addEntity(new EntityConfig(IncomeStatementItemEntity.class, IncomeStatementItemEntity.tableName));
		this.addEntity(new EntityConfig(CorpInfoEntity.class, CorpInfoEntity.tableName));
		this.addEntity(new EntityConfig(DateInfoEntity.class, DateInfoEntity.tableName));
		this.addEntity(new EntityConfig(CorpMetricEntity.class, CorpMetricEntity.tableName));
		this.addIndex(BalanceSheetReportEntity.class, new String[] { "corpId","reportDate" });
		this.addIndex(IncomeStatementReportEntity.class, new String[] { "corpId","reportDate" });
		this.addIndex(BalanceSheetItemEntity.class, new String[] { "reportId","key" });
		this.addIndex(IncomeStatementItemEntity.class, new String[] { "reportId","key" });
		this.addIndex(CorpInfoEntity.class, new String[] { "code" });
		this.addIndex(DateInfoEntity.class, new String[] { "value" });
		

	}

	public void addIndex(Class entityCls, String[] fields) {
		EntityConfig ec = this.get(entityCls);
		String name = ec.getTableName();
		for (int i = 0; i < fields.length; i++) {
			name += "_" + fields[i];
		}
		this.addIndex(new IndexConfig(entityCls, name, fields));
	}

	public void addIndex(IndexConfig ic) {
		List<IndexConfig> icL = this.indexConfigListMap.get(ic.getEntityCls());
		if (icL == null) {
			icL = new ArrayList<IndexConfig>();
			this.indexConfigListMap.put(ic.getEntityCls(), icL);
		}
		icL.add(ic);
	}

	public void addEntity(EntityConfig ec) {
		this.entityConfigList.add(ec);
		this.entityConfigMap.put(ec.getEntityClass(), ec);
	}

	public EntityConfig get(Class entityClass) {
		return this.entityConfigMap.get(entityClass);
	}

	public void initTables(ConnectionPoolWrapper pool) {
		for (EntityConfig ec : this.entityConfigList) {
			String viewSql = ec.getCreateViewSql();
			if (viewSql == null) {

				CreateTableOperation cto = new CreateTableOperation(pool, ec.getTableName());

				ec.fillCreate(cto);
				cto.execute();
			} else {
				pool.executeUpdate(viewSql);
			}

		}

	}

	public void initIndices(ConnectionPoolWrapper pool) {
		for (List<IndexConfig> icL : this.indexConfigListMap.values()) {
			for (IndexConfig ic : icL) {
				String tableName = this.get(ic.getEntityCls()).getTableName();
				String sql = "create index " + ic.getIndexName() + " on " + tableName + "(";
				String[] fs = ic.getFieldArray();
				for (int i = 0; i < fs.length; i++) {
					String f = fs[i];
					sql += f;
					if (i < fs.length - 1) {
						sql += ",";
					}
				}
				sql += ")";
				pool.executeUpdate(sql);
			}
		}
	}

}
