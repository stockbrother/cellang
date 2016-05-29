/**
 * All right is from Author of the file,to be explained in comming days.
 * Nov 9, 2012
 */
package org.cellang.clwt.core.client.lang;

/**
 * @author wu
 * 
 */
public class Position {
	
	private String value;

	protected Position(String v) {
		this.value = v;
	}

	public static Position valueOf(String v) {
		return new Position(v);
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
		if (!(obj instanceof Position)) {
			return false;
		}
		return this.value.equals(((Position) obj).value);
	}

}
