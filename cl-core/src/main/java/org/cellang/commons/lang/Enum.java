/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 27, 2012
 */
package org.cellang.commons.lang;

/**
 * @author wu
 * @deprecated
 */
public class Enum {

	private String value;

	protected Enum(String v) {
		this.value = v;
	}

	public static Enum valueOf(String v) {
		return new Enum(v);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.value;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Enum)) {
			return false;
		}
		return this.value.equals(((Enum) obj).value);
	}

}
