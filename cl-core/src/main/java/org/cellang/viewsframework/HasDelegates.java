package org.cellang.viewsframework;

public interface HasDelegates {
	public <T> T getDelegate(Class<T> cls);
}
