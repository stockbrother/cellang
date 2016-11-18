package org.cellang.viewsframework.customized;

import org.cellang.viewsframework.ext.ReportItemDefine;
import org.cellang.viewsframework.ext.ReportType;

/**
 * @see CustomizedReportUpdater
 * @author wu
 *
 */
public class XiaoShouJingLiLvCustomizedReportItemDefine extends AbstractDivideCustomizedReportItemDefine {

	XiaoShouJingLiLvCustomizedReportItemDefine() {
		super("销售净利率", ReportItemDefine.valueOf(ReportType.INCOME_STATEMENT, "净利润"),
				ReportItemDefine.valueOf(ReportType.INCOME_STATEMENT, "营业总收入"));
	}

}
