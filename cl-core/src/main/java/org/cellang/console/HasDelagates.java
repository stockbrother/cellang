package org.cellang.console;

public interface HasDelagates {
	public <T> T getDelegate(Class<T> cls);
}
