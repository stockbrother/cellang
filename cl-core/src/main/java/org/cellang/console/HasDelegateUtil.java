package org.cellang.console;

public class HasDelegateUtil {

	public static <T> T getDelegate(Object has, Class<T> type, boolean force) {
		if (!(has instanceof HasDelegates)) {
			if (force) {
				throw new RuntimeException(
						"object class:" + has.getClass() + " not support interface:" + HasDelegates.class);
			}
			return null;
		}
		T rt = ((HasDelegates) has).getDelegate(type);
		if (rt == null && force) {
			throw new RuntimeException("delegate is null.");
		}
		return rt;
	}
}
