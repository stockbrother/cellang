package org.cellang.console.customized;

import java.math.BigDecimal;

import org.cellang.commons.util.DecimalUtil;
import org.cellang.console.ext.ReportItemDefine;

/**
 * @see CustomizedReportUpdater
 * @author wu
 *
 */
public class AbstractDivideCustomizedReportItemDefine extends AbstractCustomizedReportItemDefine {

	ReportItemDefine ridA;
	ReportItemDefine ridB;

	AbstractDivideCustomizedReportItemDefine(String key, ReportItemDefine ridA, ReportItemDefine ridB) {
		super(key);
		this.ridA = ridA;
		this.ridB = ridB;
	}

	@Override
	public BigDecimal getValue(String corpId, int year) {

		BigDecimal a = this.ridA.getValue(corpId, year, esf);
		if (a == null) {
			return null;
		}

		BigDecimal b = this.ridB.getValue(corpId, year, esf);

		if (b == null) {
			return null;
		}

		return DecimalUtil.nullSafeDivide(a, b);
	}

}
