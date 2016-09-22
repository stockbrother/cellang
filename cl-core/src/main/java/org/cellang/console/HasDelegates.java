package org.cellang.console;

public interface HasDelegates {
	public <T> T getDelegate(Class<T> cls);
}
