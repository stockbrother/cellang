package com.sb.anyattribute.core;

public class AttributeName {

	private String value;

	public AttributeName(String value) {
		this.value = value;
	}

	public static AttributeName getInstance(String string) {
		//
		return new AttributeName(string);
	}

	public static boolean isNullSafeEquals(AttributeName name, AttributeName name2) {
		return name == null ? name2 == null : name.equals(name2);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof AttributeName)) {
			return false;
		}
		AttributeName ad = (AttributeName) obj;
		return this.value.equals(ad.value);
	}

	public String getValue() {
		return value;
	}

}
