package org.cellang.core.entity;

import java.util.List;

public class V0_0_3DBUpgrader extends DBUpgrader {

	public V0_0_3DBUpgrader() {
		super(DataVersion.V_0_0_2, DataVersion.V_0_0_3);
	}

	@Override
	public void doUpgrade(EntitySessionFactory esf, EntitySession es) {

		EntityConfig ec = esf.getEntityConfigFactory().getEntityConfig(PropertyEntity.class);
		List<IndexConfig> icL = esf.getEntityConfigFactory().getIndexConfigList(ec.getEntityClass());
		NewDBUpgrader.createTableAndIndex(ec, icL, es);

	}

}
