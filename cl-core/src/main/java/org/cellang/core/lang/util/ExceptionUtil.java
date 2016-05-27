package org.cellang.core.lang.util;

public class ExceptionUtil {

	public static RuntimeException toRuntimeException(Throwable t) {
		if (t instanceof RuntimeException) {
			return (RuntimeException) t;
		} else {
			return new RuntimeException(t);
		}
	}
}
