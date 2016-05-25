/**
 * All right is from Author of the file,to be explained in comming days.
 * May 12, 2013
 */
package org.cellang.webframework.client.util;

/**
 * @author wu
 * 
 */
public class ExceptionUtil {

	public static String getStacktraceAsString(Throwable throwable, String lineBreak) {
		String ret = "";
		while (throwable != null) {
			if (throwable instanceof com.google.gwt.event.shared.UmbrellaException) {
				for (Throwable thr2 : ((com.google.gwt.event.shared.UmbrellaException) throwable).getCauses()) {
					if (ret != "")
						ret += lineBreak + "Caused by: ";
					ret += thr2.toString();
					ret += lineBreak + "  at " + getStacktraceAsString(thr2, lineBreak);
				}
			} else if (throwable instanceof com.google.web.bindery.event.shared.UmbrellaException) {
				for (Throwable thr2 : ((com.google.web.bindery.event.shared.UmbrellaException) throwable)
						.getCauses()) {
					if (ret != "")
						ret += lineBreak + "Caused by: ";
					ret += thr2.toString();
					ret += lineBreak + "  at " + getStacktraceAsString(thr2, lineBreak);
				}
			} else {
				if (ret != "")
					ret += lineBreak + "Caused by: ";
				ret += throwable.toString();
				for (StackTraceElement sTE : throwable.getStackTrace())
					ret += lineBreak + "  at " + sTE;
			}
			throwable = throwable.getCause();
		}

		return ret;
	}

}
