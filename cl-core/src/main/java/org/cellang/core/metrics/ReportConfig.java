package org.cellang.core.metrics;

import org.cellang.core.entity.EntityConfig;

public class ReportConfig {

	private EntityConfig reportEntityConfig;
	private EntityConfig itemEntityConfig;

	public ReportConfig(EntityConfig entityConfig, EntityConfig entityConfig2) {
		this.reportEntityConfig = entityConfig;
		this.itemEntityConfig = entityConfig2;
	}

	public EntityConfig getReportEntityConfig() {
		return reportEntityConfig;
	}

	public void setReportEntityConfig(EntityConfig reportEntityConfig) {
		this.reportEntityConfig = reportEntityConfig;
	}

	public EntityConfig getItemEntityConfig() {
		return itemEntityConfig;
	}

	public void setItemEntityConfig(EntityConfig itemEntityConfig) {
		this.itemEntityConfig = itemEntityConfig;
	}
}
