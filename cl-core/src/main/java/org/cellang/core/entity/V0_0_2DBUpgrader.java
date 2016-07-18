package org.cellang.core.entity;

import java.sql.Connection;

import org.cellang.commons.jdbc.JdbcOperation;

public class V0_0_2DBUpgrader extends DBUpgrader {

	public V0_0_2DBUpgrader() {
		super(DataVersion.V_0_0_1, DataVersion.V_0_0_2);
	}

	@Override
	public void doUpgrade(EntitySessionFactory esf, EntitySession es) {
		String sql = "alter table " + BalanceSheetReportEntity.tableNameV001 + " rename to "
				+ BalanceSheetReportEntity.tableName;
		esf.execute(new JdbcOperation<Void>() {

			@Override
			protected Void doExecute(Connection con) {
				template.executeUpdate(con, sql);
				return null;
			}
		});

	}

}
