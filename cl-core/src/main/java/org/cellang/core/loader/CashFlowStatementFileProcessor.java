package org.cellang.core.loader;

import org.cellang.core.entity.CashFlowStatementItemEntity;
import org.cellang.core.entity.CashFlowStatementReportEntity;
import org.cellang.core.entity.EntitySessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CashFlowStatementFileProcessor
		extends AbstractReportItemFileProcessor<CashFlowStatementReportEntity, CashFlowStatementItemEntity> {
	private static final Logger LOG = LoggerFactory.getLogger(CashFlowStatementFileProcessor.class);

	public CashFlowStatementFileProcessor(EntitySessionFactory es) {
		super(es, CashFlowStatementReportEntity.class, CashFlowStatementItemEntity.class);

	}

}
