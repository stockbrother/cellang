package org.cellang.console.view;

public interface HasDelagates {
	public <T> T getDelegate(Class<T> cls);
}
