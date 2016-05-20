package com.sbrother.sbook.core;

public class SbName {

	private String value;

	public SbName(String value) {
		this.value = value;
	}

	public static SbName getInstance(String string) {
		//
		return new SbName(string);
	}

	public static boolean isNullSafeEquals(SbName name, SbName name2) {
		return name == null ? name2 == null : name.equals(name2);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof SbName)) {
			return false;
		}
		SbName ad = (SbName) obj;
		return this.value.equals(ad.value);
	}

	public String getValue() {
		return value;
	}

}
