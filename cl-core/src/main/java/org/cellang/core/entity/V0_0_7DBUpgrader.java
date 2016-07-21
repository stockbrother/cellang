package org.cellang.core.entity;

public class V0_0_7DBUpgrader extends DBUpgrader {

	public V0_0_7DBUpgrader() {
		super(DataVersion.V_0_0_6, DataVersion.V_0_0_7);
	}

	@Override
	public void doUpgrade(EntitySessionFactory esf, EntitySession es) {

		NewDBUpgrader.createTableAndIndex(esf, es, FavoriteActionEntity.class);
	}

}
