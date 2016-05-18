package com.sb.anyattribute.core;

public class AttributeOwner {

	private String value;

	public AttributeOwner(String string) {
		this.value = string;
	}

	public static AttributeOwner getInstance(String string) {
		//
		return new AttributeOwner(string);
	}

	public static boolean isNullSafeEquals(AttributeOwner owner, AttributeOwner owner2) {
		return owner == null ? owner2 == null : owner.equals(owner2);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof AttributeOwner)) {
			return false;
		}
		AttributeOwner ad = (AttributeOwner) obj;
		return this.value.equals(ad.value);
	}

	public String getValue() {
		return value;
	}

}
