/**
 * Jun 23, 2012
 */
package org.cellang.webframework.client.data;

import org.cellang.webframework.client.util.ObjectUtil;

/**
 * @author wu
 * 
 */
public class BasicData<T> {
	protected T value;

	protected BasicData(T t) {
		this.value = t;
	}

	/**
	 * @return the value
	 */
	public T getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(T value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "{" + this.value + "}";
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !obj.getClass().equals(this.getClass())) {
			return false;
		}
		T v2 = (T) ((BasicData) obj).value;
		return ObjectUtil.nullSafeEquals(this.value, v2);
	}
}
