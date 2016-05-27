package org.cellang.commons;

public class ObjectUtil {

	private String value;

	public ObjectUtil(String value) {
		this.value = value;
	}

	public static ObjectUtil getInstance(String string) {
		//
		return new ObjectUtil(string);
	}

	public static boolean isNullSafeEquals(Object name, Object name2) {
		return name == null ? name2 == null : name.equals(name2);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof ObjectUtil)) {
			return false;
		}
		ObjectUtil ad = (ObjectUtil) obj;
		return this.value.equals(ad.value);
	}

	public String getValue() {
		return value;
	}

}
