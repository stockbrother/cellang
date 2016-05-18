package com.sb.anyattribute.core;

import java.math.BigDecimal;

public class AttributeValue {
	private BigDecimal value;

	public AttributeValue(BigDecimal bigDecimal) {
		this.value = bigDecimal;
	}

	public static AttributeValue getInstance(BigDecimal bigDecimal) {
		//
		return new AttributeValue(bigDecimal);
	}

	public static boolean isNullSafeEquals(AttributeValue value2, AttributeValue value3) {
		return value2 == null ? value3 == null : value2.equals(value3);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof AttributeValue)) {
			return false;
		}
		AttributeValue ad = (AttributeValue) obj;
		return this.value.equals(ad.value);
	}

	public BigDecimal getValue() {
		return value;
	}
}
