package org.cellang.console;

public class HasDelagateUtil {

	public static <T> T getDelagate(Object has, Class<T> type, boolean force) {
		if (!(has instanceof HasDelagates)) {
			if (force) {
				throw new RuntimeException(
						"object class:" + has.getClass() + " not support interface:" + HasDelagates.class);
			}
			return null;
		}
		T rt = ((HasDelagates) has).getDelegate(type);
		if (rt == null && force) {
			throw new RuntimeException("delegate is null.");
		}
		return rt;
	}
}
