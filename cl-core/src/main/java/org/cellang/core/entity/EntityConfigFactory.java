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

	public EntityConfigFactory() {
		this.addEntity(new EntityConfig(AccountEntity.class, AccountEntity.tableName));
		this.addEntity(new EntityConfig(BalanceSheetItemEntity.class, BalanceSheetItemEntity.tableName));
		this.addEntity(new EntityConfig(BalanceSheetReportEntity.class, BalanceSheetReportEntity.tableName));
		this.addEntity(new EntityConfig(IncomeStatementReportEntity.class, IncomeStatementReportEntity.tableName));
		this.addEntity(new EntityConfig(IncomeStatementItemEntity.class, IncomeStatementItemEntity.tableName));
		this.addEntity(new EntityConfig(CorpInfoEntity.class, CorpInfoEntity.tableName));
		this.addEntity(new EntityConfig(DateInfoEntity.class, DateInfoEntity.tableName));
		this.addEntity(new EntityConfig(CorpMetricEntity.class, CorpMetricEntity.tableName));

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
}
