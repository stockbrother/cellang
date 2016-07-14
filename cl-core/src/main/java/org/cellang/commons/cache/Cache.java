package org.cellang.commons.cache;

public class Cache<T> implements Provider<T> {

	T object;

	long modified = -1;

	Provider<T> source;

	public Cache(Provider<T> source) {
		this.source = source;
	}

	@Override
	public T get() {

		if (-1 == this.modified || this.modified < source.getModified()) {
			this.object = source.get();
			this.modified = source.getModified();
		}

		return this.object;
	}

	@Override
	public long getModified() {
		return this.source.getModified();
	}

}
