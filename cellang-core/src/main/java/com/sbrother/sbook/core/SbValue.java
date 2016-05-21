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
