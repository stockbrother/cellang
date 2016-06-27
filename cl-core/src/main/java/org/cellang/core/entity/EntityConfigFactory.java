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
		this.addEntity(new EntityConfig(BalanceItemEntity.class, BalanceItemEntity.tableName));
		this.addEntity(new EntityConfig(BalanceSheetEntity.class, BalanceSheetEntity.tableName));
		this.addEntity(new EntityConfig(CorpInfoEntity.class, CorpInfoEntity.tableName));
		this.addEntity(new EntityConfig(DateInfoEntity.class, DateInfoEntity.tableName));
		this.addEntity(new EntityConfig(CorpMetricEntity.class, CorpMetricEntity.tableName));

		String sql = "select concat(t.code,to_char(t.date,'-yyyy-MM-dd')),t.code,t.date,t.fzhj,t.syzqyhj,cast(round(t.fzhj/t.syzqyhj,2) as decimal) from(" //
				+ "select "//
				+ " ci.code code"//
				+ ",di.value date"//
				+ ",("//
				+ "  select bi.value from " + BalanceItemEntity.tableName + " bi" //
				+ "  ," + BalanceSheetEntity.tableName + " bs" //
				+ "  where bi.balanceSheetId = bs.id and bs.corpId=ci.code"//
				+ "  and bi.key='资产总计' and bs.reportDate=di.value" //
				+ ") zczj" //
				+ ",("//
				+ "  select bi.value from " + BalanceItemEntity.tableName + " bi" //
				+ "  ," + BalanceSheetEntity.tableName + " bs" //
				+ "  where bi.balanceSheetId = bs.id and bs.corpId=ci.code"//
				+ "  and bi.key='所有者权益合计' and bs.reportDate=di.value" //
				+ ") syzqyhj" //
				+ ",("//
				+ "  select bi.value from " + BalanceItemEntity.tableName + " bi" //
				+ "  ," + BalanceSheetEntity.tableName + " bs" //
				+ "  where bi.balanceSheetId = bs.id and bs.corpId=ci.code"//
				+ "  and bi.key='负债合计' and bs.reportDate=di.value" //
				+ ") fzhj" //
				+ " from " + CorpInfoEntity.tableName + " ci" //
				+ "," + DateInfoEntity.tableName + " di"//
				+ ") t"//
				;
		sql = "create view " + FuzhaiQuanyiBiEntity.tableName + "(id,corpId,reportDate,fuzhai,quanyi,fzqyb) as " + sql;
		EntityConfig ec = new EntityConfig(FuzhaiQuanyiBiEntity.class, FuzhaiQuanyiBiEntity.tableName);
		ec.setCreateViewSql(sql);
		this.addEntity(ec);
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
