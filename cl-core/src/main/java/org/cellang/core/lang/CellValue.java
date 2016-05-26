package org.cellang.core.lang;

import java.math.BigDecimal;

public class CellValue {
	private BigDecimal value;

	public CellValue(BigDecimal bigDecimal) {
		this.value = bigDecimal;
	}

	public static CellValue getInstance(BigDecimal bigDecimal) {
		//
		return new CellValue(bigDecimal);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof CellValue)) {
			return false;
		}
		CellValue ad = (CellValue) obj;
		return this.value.equals(ad.value);
	}

	public BigDecimal getValue() {
		return value;
	}
}
