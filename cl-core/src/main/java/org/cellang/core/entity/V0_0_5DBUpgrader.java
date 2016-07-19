package org.cellang.core.entity;

public class V0_0_5DBUpgrader extends DBUpgrader {

	public V0_0_5DBUpgrader() {
		super(DataVersion.V_0_0_4, DataVersion.V_0_0_5);
	}

	@Override
	public void doUpgrade(EntitySessionFactory esf, EntitySession es) {

		NewDBUpgrader.createTableAndIndex(esf, es, InterestedCorpEntity.class);
	}

}
