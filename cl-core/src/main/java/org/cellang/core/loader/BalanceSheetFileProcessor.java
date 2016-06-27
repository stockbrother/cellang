package org.cellang.core.loader;

import org.cellang.core.entity.BalanceSheetItemEntity;
import org.cellang.core.entity.BalanceSheetReportEntity;
import org.cellang.core.entity.EntityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BalanceSheetFileProcessor
		extends AbstractReportItemFileProcessor<BalanceSheetReportEntity, BalanceSheetItemEntity> {
	private static final Logger LOG = LoggerFactory.getLogger(BalanceSheetFileProcessor.class);

	public BalanceSheetFileProcessor(EntityService es) {
		super(es, BalanceSheetReportEntity.class, BalanceSheetItemEntity.class);

	}

}
