package org.cellang.commons.cache;

public class Cache<T> {
	public static interface Provider<T> {
		public T get();

		public long getModified();
	}

	T object;

	long modified = -1;

	Provider<T> source;

	public Cache(Provider<T> source) {
		this.source = source;
	}

	public T get() {

		if (-1 == this.modified || this.modified < source.getModified()) {
			this.object = source.get();
			this.modified = source.getModified();
		}

		return this.object;
	}

}
