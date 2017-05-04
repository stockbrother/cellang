package org.cellang.corpsviewer.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.cellang.collector.EnvUtil;
import org.cellang.core.entity.BalanceSheetItemEntity;
import org.cellang.core.entity.BalanceSheetReportEntity;
import org.cellang.core.entity.EntityQuery;
import org.cellang.viewsframework.ops.OperationContext;
import org.cellang.viewsframework.report.ReportTableDataProvider.GetReportOp;

public class EntityServiceUtil {

	public static List<List<BalanceSheetItemEntity>> queryBalanceSheetItem(OperationContext oc, String corpId, int year,
			int years) {
		List<List<BalanceSheetItemEntity>> rt = new ArrayList<>();

		for (int i = 0; i < years; i++) {
			List<BalanceSheetItemEntity> lI = queryBalanceSheetItem(oc, corpId, year);
			year--;
			rt.add(lI);
		}

		return rt;
	}

	public static List<BalanceSheetItemEntity> queryBalanceSheetItem(OperationContext oc, String corpId, int year) {
		Date date = EnvUtil.newDateOfYearLastDay(year);
		GetReportOp<BalanceSheetReportEntity> getRpt = new GetReportOp<>();
		BalanceSheetReportEntity bsr = getRpt.set(BalanceSheetReportEntity.class, corpId, date)
				.execute(oc.getEntityService());

		if (bsr == null) {
			return new ArrayList<>();
		}
		String reportId = bsr.getId();
		List<BalanceSheetItemEntity> el = new EntityQuery<BalanceSheetItemEntity>(
				oc.getEntityConfigFactory().getEntityConfig(BalanceSheetItemEntity.class), new String[] { "reportId" },
				new Object[] { reportId }).offset(0).execute(oc.getEntityService());

		return (List<BalanceSheetItemEntity>) el;
	}

}
