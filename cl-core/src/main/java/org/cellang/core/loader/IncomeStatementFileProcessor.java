package org.cellang.core.loader;

import org.cellang.core.entity.EntitySessionFactory;
import org.cellang.core.entity.IncomeStatementItemEntity;
import org.cellang.core.entity.IncomeStatementReportEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IncomeStatementFileProcessor
		extends AbstractReportItemFileProcessor<IncomeStatementReportEntity, IncomeStatementItemEntity> {
	private static final Logger LOG = LoggerFactory.getLogger(IncomeStatementFileProcessor.class);

	public IncomeStatementFileProcessor(EntitySessionFactory es) {
		super(es, IncomeStatementReportEntity.class, IncomeStatementItemEntity.class);

	}

}
