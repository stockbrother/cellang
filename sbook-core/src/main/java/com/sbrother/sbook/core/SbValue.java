package com.sbrother.sbook.core;

import java.math.BigDecimal;

public class SbValue {
	private BigDecimal value;

	public SbValue(BigDecimal bigDecimal) {
		this.value = bigDecimal;
	}

	public static SbValue getInstance(BigDecimal bigDecimal) {
		//
		return new SbValue(bigDecimal);
	}

	public static boolean isNullSafeEquals(SbValue value2, SbValue value3) {
		return value2 == null ? value3 == null : value2.equals(value3);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof SbValue)) {
			return false;
		}
		SbValue ad = (SbValue) obj;
		return this.value.equals(ad.value);
	}

	public BigDecimal getValue() {
		return value;
	}
}
