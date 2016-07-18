package org.cellang.core.entity;

public abstract class DBUpgrader {

	protected DataVersion sourceVersion;
	protected DataVersion targetVersion;

	public DBUpgrader(DataVersion source, DataVersion target) {
		this.sourceVersion = source;
		this.targetVersion = target;
	}

	public DataVersion getTargetVersion() {
		return this.targetVersion;
	}

	public void upgrade(EntitySessionFactory esf) {
		esf.execute(new EntityOp<Void>() {

			@Override
			public Void execute(EntitySession es) {
				doUpgrade(esf, es);
				doAfterUpgrade(esf, es);
				return null;
			}
		});

	}

	protected void doAfterUpgrade(EntitySessionFactory esf, EntitySession es) {
		if (this.targetVersion.isGreatOrEquals(DataVersion.V_0_0_3)) {
			String sql = "update " + PropertyEntity.tableName + " set value=? where category=? and key=?";
			es.getDataAccessTemplate().executeUpdate(es.getConnection(), sql,
					new Object[] { this.targetVersion.toString(), "core", "data-version" });
		}
	}

	public abstract void doUpgrade(EntitySessionFactory esf, EntitySession es);

	public DataVersion getSourceVersion() {
		return sourceVersion;
	}

}
