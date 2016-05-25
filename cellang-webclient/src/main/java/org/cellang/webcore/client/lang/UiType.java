/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 1, 2012
 */
package org.cellang.webcore.client.lang;

/**
 * @author wu
 * 
 */
public class UiType<T> {
	private static int nextHashCode;
	private final int index;
	private UiType<?> parent;

	/**
	 * Constructor.
	 */
	public UiType() {
		this(null);
	}

	public UiType(UiType<?> p) {
		index = ++nextHashCode;
		this.parent = p;
	}

	/**
	 * @return the parent
	 */
	public UiType<?> getParent() {
		return parent;
	}

	public boolean isSuperType(UiType<?> type, boolean includeMe) {
		UiType<?> from = this.parent;
		if (includeMe) {
			from = this;
		}
		while (from != null) {
			if (from == type) {
				return true;
			}
			from = from.getParent();
		}
		return false;
	}

	public boolean isSubType(UiType<?> type, boolean includeMe) {
		return type.isSuperType(this, includeMe);
	}

	@Override
	public final int hashCode() {
		return index;
	}

	@Override
	public String toString() {
		return "idx:" + this.index + ",parent:" + parent;
	}

	@Override
	public boolean equals(Object obj) {
		return obj == this;
	}
}
