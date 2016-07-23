package org.cellang.core.entity;

/**
 * @see EntitySessionFactoryImpl
 * @author wu
 *
 */
public class V0_0_8DBUpgrader extends DBUpgrader {

	public V0_0_8DBUpgrader() {
		super(DataVersion.V_0_0_7, DataVersion.V_0_0_8);
	}

	@Override
	public void doUpgrade(EntitySessionFactory esf, EntitySession es) {
		EntityConfig ec = esf.getEntityConfigFactory().get(ExtendingPropertyEntity.class);
		NewDBUpgrader.createIndex(esf, ec, es);
	}

}
