package org.cellang.viewsframework.control;

import java.util.HashMap;
import java.util.Map;

import org.cellang.viewsframework.HasDelegates;

public class DefaultHasDelagates implements HasDelegates {

	private Map<Class, Object> interfaceObjectMap = new HashMap<>();

	public <T> boolean putIf(Class<T> cls, Object obj) {
		if (cls.isInstance(obj)) {
			this.put(cls, (T) obj);
			return true;
		}
		return false;
	}

	public <T> void put(Class<T> cls, T obj) {
		this.interfaceObjectMap.put(cls, obj);
	}

	@Override
	public <T> T getDelegate(Class<T> cls) {
		return (T) this.interfaceObjectMap.get(cls);
	}
}
