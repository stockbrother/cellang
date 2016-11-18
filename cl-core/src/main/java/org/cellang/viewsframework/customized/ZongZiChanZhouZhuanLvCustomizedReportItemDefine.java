package org.cellang.viewsframework.customized;

import org.cellang.viewsframework.ext.ReportItemDefine;
import org.cellang.viewsframework.ext.ReportType;
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
