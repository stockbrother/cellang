package com.sbrother.sbook.core;

public class SbOwner {

	private String value;

	public SbOwner(String string) {
		this.value = string;
	}

	public static SbOwner getInstance(String string) {
		//
		return new SbOwner(string);
	}

	public static boolean isNullSafeEquals(SbOwner owner, SbOwner owner2) {
		return owner == null ? owner2 == null : owner.equals(owner2);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof SbOwner)) {
			return false;
		}
		SbOwner ad = (SbOwner) obj;
		return this.value.equals(ad.value);
	}

	public String getValue() {
		return value;
	}

}
