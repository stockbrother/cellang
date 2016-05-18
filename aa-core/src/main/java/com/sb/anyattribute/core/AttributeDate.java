package com.sb.anyattribute.core;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class AttributeDate {

	private static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");

	private long value;

	public AttributeDate(long timestamp) {
		this.value = timestamp;
	}
	
	public static AttributeDate getInstance(long value) {
		return new AttributeDate(value);
	}
	
	public static AttributeDate getInstance(String string) {
		long ts;
		try {
			ts = FORMAT.parse(string).getTime();
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
		return AttributeDate.getInstance(ts);
	}

	public static boolean isNullSafeEquals(AttributeDate date, AttributeDate date2) {
		return date == null ? date2 == null : date.equals(date2);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof AttributeDate)) {
			return false;
		}
		AttributeDate ad = (AttributeDate) obj;
		return this.value == ad.value;
	}

	public long getValue() {
		return value;
	}

}
