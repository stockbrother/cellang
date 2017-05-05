package org.cellang.corpsviewer;

import org.cellang.core.entity.QingSuanReportEntity;
import org.cellang.viewsframework.ops.OperationContext;
import org.cellang.viewsframework.report.ReportTableView;

public class QingSuanReportView extends ReportTableView<QingSuanReportEntity> {

	public QingSuanReportView(OperationContext oc, int years, String corpId) {
		super(oc, QingSuanReportEntity.class, years, corpId);
	}

}
