package org.cellang.core.entity;

public class V0_0_6DBUpgrader extends DBUpgrader {

	public V0_0_6DBUpgrader() {
		super(DataVersion.V_0_0_5, DataVersion.V_0_0_6);
	}

	@Override
	public void doUpgrade(EntitySessionFactory esf, EntitySession es) {

		NewDBUpgrader.createTableAndIndex(esf, es, ExtendingPropertyEntity.class);
	}

}
