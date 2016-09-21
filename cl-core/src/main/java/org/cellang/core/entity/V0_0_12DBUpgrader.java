package org.cellang.core.entity;

/**
 * @see EntitySessionFactoryImpl
 * @author wu
 *
 */
public class V0_0_12DBUpgrader extends DBUpgrader {

	public V0_0_12DBUpgrader() {
		super(DataVersion.V_0_0_11, DataVersion.V_0_0_12);
	}

	@Override
	public void doUpgrade(EntitySessionFactory esf, EntitySession es) {
		NewDBUpgrader.createTableAndIndex(esf, es, CorpGroupEntity.class);
		NewDBUpgrader.createTableAndIndex(esf, es, GroupCorpEntity.class);
	}

}
