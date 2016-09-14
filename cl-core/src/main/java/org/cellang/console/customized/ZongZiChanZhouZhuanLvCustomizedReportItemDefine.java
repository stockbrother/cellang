package org.cellang.console.customized;

import org.cellang.console.ext.ReportItemDefine;
import org.cellang.console.ext.ReportType;
/**
 * @see CustomizedReportUpdater
 * @author wu
 *
 */
public class ZongZiChanZhouZhuanLvCustomizedReportItemDefine  extends AbstractDivideCustomizedReportItemDefine {

	ZongZiChanZhouZhuanLvCustomizedReportItemDefine() {
		super("总资产周转率", ReportItemDefine.valueOf(ReportType.INCOME_STATEMENT, "营业总收入"),
				ReportItemDefine.valueOf(ReportType.BALANCE_SHEET, "资产总计"));
	}

}
