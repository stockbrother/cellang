package org.cellang.core.entity;

/**
 * @see EntitySessionFactoryImpl
 * @author wu
 *
 */
public class V0_0_13DBUpgrader extends DBUpgrader {

	public V0_0_13DBUpgrader() {
		super(DataVersion.V_0_0_12, DataVersion.V_0_0_13);
	}

	@Override
	public void doUpgrade(EntitySessionFactory esf, EntitySession es) {
		NewDBUpgrader.createTableAndIndex(esf, es, QingSuanReportEntity.class);
		NewDBUpgrader.createTableAndIndex(esf, es, QingSuanItemEntity.class);
	}

}
