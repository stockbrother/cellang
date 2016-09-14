package org.cellang.console.customized;

import org.cellang.console.ext.ReportItemDefine;
import org.cellang.console.ext.ReportType;

/**
 * @see CustomizedReportUpdater
 * @author wu
 *
 */
public class QuanYiChengShuCustomizedReportItemDefine extends AbstractDivideCustomizedReportItemDefine {

	QuanYiChengShuCustomizedReportItemDefine() {
		super("权益乘数", ReportItemDefine.valueOf(ReportType.BALANCE_SHEET, "资产总计"),
				ReportItemDefine.valueOf(ReportType.BALANCE_SHEET, "所有者权益合计"));
	}

}
