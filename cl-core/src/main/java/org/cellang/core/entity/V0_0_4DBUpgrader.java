package org.cellang.core.entity;

public class V0_0_4DBUpgrader extends DBUpgrader {

	public V0_0_4DBUpgrader() {
		super(DataVersion.V_0_0_3, DataVersion.V_0_0_4);
	}

	@Override
	public void doUpgrade(EntitySessionFactory esf, EntitySession es) {

		// add dataVersion
		PropertyEntity pe = new PropertyEntity();
		String category = "core";
		String key = "data-version";
		String value = this.getTargetVersion().toString();
		pe.setId(category + "." + key);
		pe.setCategory(category);
		pe.setKey(key);
		pe.setValue(value);
		es.save(pe);//
	}

}
