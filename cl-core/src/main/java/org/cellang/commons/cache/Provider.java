package org.cellang.commons.cache;

public interface Provider<T> {
	public T get();

	public long getModified();
}
