package org.cellang.core.entity;

/**
 * @see EntitySessionFactoryImpl
 * @author wu
 *
 */
public class V0_0_10DBUpgrader extends DBUpgrader {

	public V0_0_10DBUpgrader() {
		super(DataVersion.V_0_0_9, DataVersion.V_0_0_10);
	}

	@Override
	public void doUpgrade(EntitySessionFactory esf, EntitySession es) {
		NewDBUpgrader.createTableAndIndex(esf, es, CashFlowStatementReportEntity.class);
		NewDBUpgrader.createTableAndIndex(esf, es, CashFlowStatementItemEntity.class);
	}

}
