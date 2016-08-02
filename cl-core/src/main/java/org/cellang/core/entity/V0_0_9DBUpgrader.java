package org.cellang.core.entity;

/**
 * @see EntitySessionFactoryImpl
 * @author wu
 *
 */
public class V0_0_9DBUpgrader extends DBUpgrader {

	public V0_0_9DBUpgrader() {
		super(DataVersion.V_0_0_8, DataVersion.V_0_0_9);
	}

	@Override
	public void doUpgrade(EntitySessionFactory esf, EntitySession es) {
		NewDBUpgrader.createTableAndIndex(esf, es, CustomizedReportEntity.class);
		NewDBUpgrader.createTableAndIndex(esf, es, CustomizedItemEntity.class);
	}

}
