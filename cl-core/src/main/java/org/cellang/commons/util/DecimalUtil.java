package org.cellang.commons.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class DecimalUtil {

	public static BigDecimal nullSafeDivide(BigDecimal a, BigDecimal b) {
		if (a == null || b == null || b.compareTo(BigDecimal.ZERO) == 0) {
			return null;
		}
		return a.divide(b, 2, RoundingMode.HALF_UP);
	}
}
