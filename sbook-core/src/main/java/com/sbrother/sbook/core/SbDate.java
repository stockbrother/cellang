package com.sbrother.sbook.core;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class SbDate {

	private static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");

	private long value;

	public SbDate(long timestamp) {
		this.value = timestamp;
	}
	
	public static SbDate getInstance(long value) {
		return new SbDate(value);
	}
	
	public static SbDate getInstance(String string) {
		long ts;
		try {
			ts = FORMAT.parse(string).getTime();
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
		return SbDate.getInstance(ts);
	}

	public static boolean isNullSafeEquals(SbDate date, SbDate date2) {
		return date == null ? date2 == null : date.equals(date2);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof SbDate)) {
			return false;
		}
		SbDate ad = (SbDate) obj;
		return this.value == ad.value;
	}

	public long getValue() {
		return value;
	}

}
