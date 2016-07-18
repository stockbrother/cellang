package org.cellang.core.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		this.addEntity(new EntityConfig(QuotesEntity.class, QuotesEntity.tableName));
		this.addEntity(new EntityConfig(InterestedCorpEntity.class, InterestedCorpEntity.tableName));
		this.addEntity(new EntityConfig(PropertyEntity.class, PropertyEntity.tableName));

		// indices
		this.addIndex(BalanceSheetReportEntity.class, new String[] { "corpId", "reportDate" });
		this.addIndex(IncomeStatementReportEntity.class, new String[] { "corpId", "reportDate" });
		this.addIndex(BalanceSheetItemEntity.class, new String[] { "reportId", "key" });
		this.addIndex(IncomeStatementItemEntity.class, new String[] { "reportId", "key" });
		this.addIndex(CorpInfoEntity.class, new String[] { "code" });
		this.addIndex(DateInfoEntity.class, new String[] { "value" });
		this.addIndex(QuotesEntity.class, new String[] { "code" });
		this.addIndex(InterestedCorpEntity.class, new String[] { "corpId" });
		this.addIndex(PropertyEntity.class, new String[] { "category" });

	}

	public EntityConfig getEntityConfig(Class entitCls) {
		return entityConfigMap.get(entitCls);
	}

	public List<EntityConfig> getEntityConfigList() {
		return new ArrayList<EntityConfig>(this.entityConfigList);
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

	public List<IndexConfig> getIndexConfigList(Class entityCls) {
		List<IndexConfig> rt = new ArrayList<IndexConfig>();
		List<IndexConfig> icL = this.indexConfigListMap.get(entityCls);
		if (icL != null) {
			rt.addAll(icL);
		}
		return rt;
	}

	public void addEntity(EntityConfig ec) {
		this.entityConfigList.add(ec);
		this.entityConfigMap.put(ec.getEntityClass(), ec);
	}

	public EntityConfig get(Class entityClass) {
		return this.entityConfigMap.get(entityClass);
	}

}
