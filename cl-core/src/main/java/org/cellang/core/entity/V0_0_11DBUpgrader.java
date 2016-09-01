package org.cellang.core.entity;

/**
 * @see EntitySessionFactoryImpl
 * @author wu
 *
 */
public class V0_0_11DBUpgrader extends DBUpgrader {

	public V0_0_11DBUpgrader() {
		super(DataVersion.V_0_0_10, DataVersion.V_0_0_11);
	}

	@Override
	public void doUpgrade(EntitySessionFactory esf, EntitySession es) {
		NewDBUpgrader.createIndex(esf, es, CashFlowStatementReportEntity.class);
		NewDBUpgrader.createIndex(esf, es, CashFlowStatementItemEntity.class);
	}

}
