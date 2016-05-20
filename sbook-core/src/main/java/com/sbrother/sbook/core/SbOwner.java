package com.sbrother.sbook.core;

public class SbOwner {

	private String identifier;

	public SbOwner(String string) {
		this.identifier = string;
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
		return this.identifier.equals(ad.identifier);
	}

	public String getIdentifier() {
		return identifier;
	}

}
